/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

import Canvas from "diagram-js/lib/core/Canvas";
import ElementRegistry from "diagram-js/lib/core/ElementRegistry";
import {Connection, Element, ElementLike, Shape} from "diagram-js/lib/model/Types";
import BpmnViewer from "../bpm/js/BpmnViewer";
import {ElementMarkerType} from "../types";

/** position of a finished activity pass in the process start order, used as a logical timestamp */
type ActivityIndex = number;

/** a finished pass that could have fed a later activity, with the flow it would have arrived on */
interface SequenceFlowCandidate {
    sequenceFlow: Connection;
    sourceActivityIndex: ActivityIndex;
}

export class MarkerManager {
    /** markers whose elements keep the normal presentation when the diagram is shown as disabled */
    private static readonly TOKEN_HISTORY_MARKERS: ElementMarkerType[] =
        [ElementMarkerType.RUNNING_ACTIVITY, ElementMarkerType.FINISHED_ACTIVITY, ElementMarkerType.PASSED_FLOW];

    /** mirrored to the external labels of marked elements so they are highlighted together */
    private static readonly MARKED_ELEMENT_LABEL_MARKER = "marked-element-label";

    private elementRegistry: ElementRegistry;
    private canvas: Canvas;

    constructor(viewer: BpmnViewer) {
        this.elementRegistry = viewer.get<ElementRegistry>("elementRegistry");
        this.canvas = viewer.get<Canvas>("canvas");
    }

    /**
     * Adds the specified marker to the specified element.
     * @param elementId diagram element id
     * @param marker a marker to add
     */
    public addMarker(elementId: string, marker: ElementMarkerType) {
        this.canvas.addMarker(elementId, marker);
        this.highlightElementLabels(elementId);
    }

    /**
     * Removes the specified marker for the specified element.
     * @param elementId diagram element id
     * @param marker a marker to remove
     */
    public removeMarker(elementId: string, marker: ElementMarkerType) {
        this.canvas.removeMarker(elementId, marker);
        this.highlightElementLabels(elementId);
    }


    /**
     * Adds markers to all sequences flows that are passed between the specified finished and running activities.
     * List of finished and running activities can contain duplication if the element passed multiple times.
     *
     * @param finishedActivities ids of finished activities taken from the list of activity instances sorted by occurrence.
     * @param runningActivities ids of running activities taken from the list of activity instances
     */
    public highlightPassedFlows(finishedActivities: string[], runningActivities: string[]) {
        const passedSequenceFlowIds = this.findPassedSequenceFlowIds(finishedActivities, runningActivities);

        this.elementRegistry.forEach((element) => {
            if (element.type !== "bpmn:SequenceFlow") {
                return;
            }
            const flow = element as Connection;
            if (!flow.source || !flow.target) {
                return;
            }
            const isPassed = passedSequenceFlowIds.has(flow.id);
            const hasMarker = this.canvas.hasMarker(flow, ElementMarkerType.PASSED_FLOW);
            if (isPassed && !hasMarker) {
                this.canvas.addMarker(flow, ElementMarkerType.PASSED_FLOW);
            } else if (!isPassed && hasMarker) {
                this.canvas.removeMarker(flow, ElementMarkerType.PASSED_FLOW);
            }
            this.highlightElementLabels(flow);
        });
    }

    // external labels are separate diagram elements, so they must be (un)highlighted with their element
    private highlightElementLabels(elementOrId: ElementLike | string) {
        // the canvas marker API only takes shapes and connections, not the wider ElementLike
        const element = (typeof elementOrId === "string"
            ? this.elementRegistry.get(elementOrId)
            : elementOrId) as Shape | Connection | undefined;
        const labels = element?.labels ?? [];
        if (!element || !labels.length) {
            return;
        }
        const hasMarker = MarkerManager.TOKEN_HISTORY_MARKERS
            .some((marker) => this.canvas.hasMarker(element, marker));
        labels.forEach((label) => {
            const labelHasMarker = this.canvas.hasMarker(label, MarkerManager.MARKED_ELEMENT_LABEL_MARKER);
            if (hasMarker && !labelHasMarker) {
                this.canvas.addMarker(label, MarkerManager.MARKED_ELEMENT_LABEL_MARKER);
            } else if (!hasMarker && labelHasMarker) {
                this.canvas.removeMarker(label, MarkerManager.MARKED_ELEMENT_LABEL_MARKER);
            }
        });
    }

    /**
     * Reconstructs which sequence flows the process took: the engine reports only the activities
     * that ran, in occurrence order, never the flows between them. This is a heuristic: routes visiting
     * the same activities in the same order cannot be told apart.
     */
    private findPassedSequenceFlowIds(finishedActivities: string[], runningActivities: string[]): Set<string> {
        const passedSequenceFlowIds = new Set<string>();
        // activity indexes of each activity's finished passes, in occurrence order
        const finishedIndexesByActivityId = new Map<string, ActivityIndex[]>();

        // source passes that already have a passed outgoing sequence flow
        const sourceIndexesOfPassedFlows = new Set<ActivityIndex>();

        finishedActivities.forEach((activityId, activityIndex) => {
            // match before recording the pass, so a pass cannot feed itself through a self-loop
            const passedSequenceFlow = this.matchIncomingSequenceFlow(activityId,
                activityIndex, finishedIndexesByActivityId, sourceIndexesOfPassedFlows);
            if (passedSequenceFlow) {
                passedSequenceFlowIds.add(passedSequenceFlow.id);
            }
            const finishedIndexes = finishedIndexesByActivityId.get(activityId);
            if (finishedIndexes) {
                finishedIndexes.push(activityIndex);
            } else {
                finishedIndexesByActivityId.set(activityId, [activityIndex]);
            }
        });

        runningActivities.forEach((activityId) => {
            // a running activity started after every finished pass, so all of them are earlier
            const passedSequenceFlow = this.matchIncomingSequenceFlow(activityId,
                finishedActivities.length, finishedIndexesByActivityId, sourceIndexesOfPassedFlows);

            if (passedSequenceFlow) {
                passedSequenceFlowIds.add(passedSequenceFlow.id);
            }
        });

        return passedSequenceFlowIds;
    }

    /**
     * Finds the sequence flow the pass at targetActivityIndex was entered through, or
     * {@code null} when no source activity ran before it.
     * <p>
     * Prefers the candidate that ran last and has no passed outgoing flow yet, and records it, so
     * a join reached once per branch claims a different branch on every pass. When every candidate
     * already has one, the latest is reused without recording, so a passed flow is never dropped.
     */
    private matchIncomingSequenceFlow(activityId: string, targetActivityIndex: ActivityIndex,
                                      finishedIndexesByActivityId: Map<string, ActivityIndex[]>,
                                      sourceIndexesOfPassedFlows: Set<ActivityIndex>): Connection | null {
        const element = this.elementRegistry.get(activityId) as Element | undefined;
        if (!element) {
            return null;
        }
        const incomingSequenceFlows = element.incoming ?? [];

        // find all candidates for sequence flows + passed source activity of flow
        const flowCandidates: SequenceFlowCandidate[] = this.collectSequenceFlowCandidates(
            incomingSequenceFlows, targetActivityIndex, finishedIndexesByActivityId);

        // find all flow candidates that are not proceeded yet
        const candidatesWithFreeSource = flowCandidates.filter(
            (candidate) => !sourceIndexesOfPassedFlows.has(candidate.sourceActivityIndex));

        // detect the latest executed
        const latestFreeSourceCandidate = this.findLatestCandidate(candidatesWithFreeSource);
        if (latestFreeSourceCandidate) {
            sourceIndexesOfPassedFlows.add(latestFreeSourceCandidate.sourceActivityIndex);
            return latestFreeSourceCandidate.sequenceFlow;
        }
        // every earlier source already has a passed flow: one pass fed several branches, so reuse it
        return this.findLatestCandidate(flowCandidates)?.sequenceFlow ?? null;
    }

    private collectSequenceFlowCandidates(incomingSequenceFlows: Connection[],
                                          targetActivityIndex: ActivityIndex,
                                          finishedIndexesByActivityId: Map<string, ActivityIndex[]>): SequenceFlowCandidate[] {
        // for each incoming flow, take source activity and find all instances started before the target one
        const candidates: SequenceFlowCandidate[] = [];
        for (const sequenceFlow of incomingSequenceFlows) {
            const sourceElement = sequenceFlow.source;
            if (!sourceElement) {
                continue;
            }
            const sourceActivityInstances = finishedIndexesByActivityId.get(sourceElement.id) ?? [];
            for (const sourceActivityIndex of sourceActivityInstances) {
                if (sourceActivityIndex < targetActivityIndex) {
                    candidates.push({sequenceFlow, sourceActivityIndex});
                }
            }
        }
        return candidates;
    }

    private findLatestCandidate(candidates: SequenceFlowCandidate[]): SequenceFlowCandidate | null {
        let latestFlow: SequenceFlowCandidate | null = null;
        // find the sequence flow with a source activity that is passed latest
        // when two flows share a source, the first flow wins
        for (const candidate of candidates) {
            if (!latestFlow || candidate.sourceActivityIndex > latestFlow.sourceActivityIndex) { // "greater" index = "later" execution
                latestFlow = candidate;
            }
        }
        return latestFlow;
    }
}
