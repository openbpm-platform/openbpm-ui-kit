/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

import Overlays, {OverlayAttrs} from "diagram-js/lib/features/overlays/Overlays";
import {createIncidentOverlay} from "./createIncidentOverlay";
import {
    ActivityInstanceStatisticsOverlayData,
    CalledInstancesOverlayParams,
    CalledProcessOverlaysParams,
    DecisionInstanceLinkOverlayParams,
    DecisionLinkOverlaysParams,
    DocumentationOverlayParams,
    IncidentOverlayData,
    NewActivityStatisticsOverlayData,
    OverlayData,
    OverlayPosition,
    OverlayType,
    SendMessageOverlaysData,
    SendMessageOverlaysParams,
    VariableChangeOverlayCmd
} from "./types";
import {createDocumentationOverlay} from "./createDocumentationOverlay";
import BpmnViewer from "../bpm/js/BpmnViewer";
import ElementRegistry from 'diagram-js/lib/core/ElementRegistry';
import Canvas from 'diagram-js/lib/core/Canvas';
import {createNavigationOverlay} from "./createNavigationOverlay";
import {getMessage, isMessageSupported} from "../utils/eventDefinitionUtils";
import {createSendMessageOverlay} from "./createSendMessageOverlay";
import {getBinding, getCalledElement, getVersion, getVersionTag} from "../utils/callActivityUtils";
import {ElementLike} from "diagram-js/lib/model/Types";
import {findElementDocumentation} from "../utils/documentationUtils";
import {createActivityStatisticsOverlay} from "./createActivityStatisticsOverlay";
import {createActivityInstanceStatisticsOverlay} from "./createActivityInstanceStatisticsOverlay";
import {getElementTransactionBoundary} from "../utils/transactionBoundaryUtils";
import {forEach} from 'min-dash';
import {createTransactionBoundaryOverlay} from "./createTransactionBoundaryOverlay";
import {BeforeElementTransactionType, ElementMarkerType, ElementTransactionBoundary} from "../types";
import {Point, Rect} from "diagram-js/lib/util/Types";
import {createAnimationOverlay} from "./createAnimationOverlay";
import {
    getDecisionBinding,
    getDecisionRef,
    getDecisionVersion,
    getDecisionVersionTag
} from "../utils/businessRuleTaskUtils";
import {createVariableChangeOverlay} from "./createVariableChangeOverlay";

/**
 * OverlayManager class manages various overlays associated with BPMN diagram elements,
 * offering functionalities such as showing incident overlays, documentation links,
 * navigation options, and other interactive visual indicators.
 */
export class OverlayManager {
    private overlays: Overlays;
    private elementRegistry: ElementRegistry;
    private canvas: Canvas;

    constructor(viewer: BpmnViewer) {
        this.overlays = viewer.get<Overlays>("overlays");
        this.elementRegistry = viewer.get<ElementRegistry>("elementRegistry");
        this.canvas = viewer.get<Canvas>("canvas");
    }

    /**
     * Adds an overlay with incident count for the specified element.
     * @param data overlay data
     */
    public showIncidentOverlay(data: IncidentOverlayData) {
        const incidentOverlay = createIncidentOverlay({
            tooltipMessage: data.tooltipMessage,
            incidentCount: data.incidentCount
        });
        this.overlays.add(data.elementId, OverlayType.INCIDENT_COUNT, incidentOverlay);
    }

    /**
     * Adds or removes overlays for the all elements that allow viewing element documentation.
     * @param data overlays data
     * @param handleClick overlay click handler
     */
    public showDocumentationOverlay({data: {showDocumentationOverlay}, handleClick}: DocumentationOverlayParams) {
        this.overlays.remove({type: OverlayType.DOCUMENTATION});

        if (showDocumentationOverlay) {
            const elements: ElementLike[] = this.elementRegistry.filter(element => {
                return element.type !== 'label';
            });

            elements.forEach((element: ElementLike) => {
                const documentation = findElementDocumentation(element);

                if (!documentation) {
                    return;
                }
                const documentationValue: string = documentation.text;

                const handleOverlayClick = () => {
                    handleClick(element, documentationValue);
                };

                const sendMessageOverlay = this.overlays.get({element: element.id, type: OverlayType.SEND_MESSAGE});
                let customPosition: OverlayPosition;
                if (sendMessageOverlay && Array.isArray(sendMessageOverlay) && sendMessageOverlay.length > 0) {
                    // show a documentation overlay on top of the "send message" overlay
                    customPosition = {
                        top: sendMessageOverlay[0].position.top - 25,
                        left: sendMessageOverlay[0].position.left
                    }
                }

                const documentationOverlay: OverlayAttrs = createDocumentationOverlay({
                    element,
                    title: documentationValue,
                    handleClick: handleOverlayClick,
                    customPosition,
                    rootClass: sendMessageOverlay ? "overlay-group-item-root" : undefined
                });

                this.overlays.add(element.id, OverlayType.DOCUMENTATION, documentationOverlay);
            });
        }
    }

    /**
     * Adds an overlay to navigate to the called decision instance from the Business Rule Task element.
     * @param data overlay data
     * @param handleClick overlay click handler
     */
    public showDecisionInstanceLinkOverlay({data, handleClick}: DecisionInstanceLinkOverlayParams) {
        const element = this.elementRegistry.find((element) => {
            return element.type == "bpmn:BusinessRuleTask" && element.id == data.activityId
        });

        if (element) {
            const handleOverlayClick = () => {
                handleClick(data.decisionInstanceId);
            }
            const decisionInstanceOverlay = createNavigationOverlay({
                title: data.tooltipMessage,
                handleClick: handleOverlayClick
            });

            this.overlays.add(element.id, OverlayType.DECISION_INSTANCE, decisionInstanceOverlay);
        }
    }

    /**
     * Adds the "Send message" overlay for each supported element having the message reference.
     * @param data overlays data
     * @param handleClick overlay click handler
     */
    public showSendMessageOverlays({data, handleClick}: SendMessageOverlaysParams) {
        const elements = this.elementRegistry.filter(function (element: ElementLike) {
            return isMessageSupported(element);
        });

        elements.forEach((element: ElementLike) => {
            const message = getMessage(element);
            let canSendMessage = this.shouldRenderSendMessageOverlay(element, data);
            if (canSendMessage) {
                const handleOverlayClick = () => {
                    const details = <JSON><unknown>{
                        "messageName": message.get('name'),
                        "elementId": element.id,
                        "elementType": element.type,
                        "elementName": element.businessObject.name,
                    }
                    handleClick(details);
                }

                const overlayTooltip = `${data.tooltipMessage}: ${message.get("name")}`;
                const sendMessageOverlay: OverlayAttrs = createSendMessageOverlay({
                    title: overlayTooltip,
                    handleClick: handleOverlayClick
                });
                this.overlays.add(element.id, OverlayType.SEND_MESSAGE, sendMessageOverlay);
            }
        });
    }

    /**
     * Adds a clickable overlay for navigating to the called instances from the specified Call activity element.
     * @param data overlay data
     * @param handleClick overlay click handler
     */
    public showCalledInstances({data, handleClick}: CalledInstancesOverlayParams) {
        const handleOverlayClick = () => {
            const details = <JSON><unknown>{
                "processInstanceIds": data.processInstanceIds
            }
            handleClick(details);
        }

        const calledInstancesOverlay = createNavigationOverlay({
            title: data.tooltipMessage,
            handleClick: handleOverlayClick
        });

        this.overlays.add(data.elementId, OverlayType.CALLED_PROCESS_INSTANCE, calledInstancesOverlay);
    }

    /**
     * Adds a clickable overlay for navigating to the called process for each Call activity element on the diagram.
     * @param data overlay data
     * @param handleClick overlay click handler
     */
    public showCalledProcessOverlays({data, handleClick}: CalledProcessOverlaysParams) {
        this.overlays.remove({type: OverlayType.CALLED_PROCESS});

        if (data.visible) {
            const elements: ElementLike[] = this.elementRegistry.filter(element => element.type == "bpmn:CallActivity");
            elements.forEach((element: ElementLike) => {
                const calledElement = getCalledElement(element);
                if (calledElement && calledElement.length > 0) {
                    const handleOverlayClick = () => {
                        const callActivityData = <JSON><unknown>{
                            "calledElement": calledElement,
                            "version": getVersion(element),
                            "versionTag": getVersionTag(element),
                            "binding": getBinding(element),
                        };

                        handleClick(element, callActivityData);
                    }

                    const tooltipMessage = `${data.tooltipMessage} (${calledElement})`;
                    const calledProcessOverlay = createNavigationOverlay({
                        title: tooltipMessage,
                        handleClick: handleOverlayClick
                    });

                    this.overlays.add(element.id, OverlayType.CALLED_PROCESS, calledProcessOverlay);
                }
            });
        }
    }

    public showDecisionLinkOverlays({data, handleClick}: DecisionLinkOverlaysParams) {
        this.overlays.remove({type: OverlayType.DECISION});

        if (data.visible) {
            const elements: ElementLike[] = this.elementRegistry.filter(element => element.type == "bpmn:BusinessRuleTask");
            elements.forEach((element: ElementLike) => {
                const decisionRef = getDecisionRef(element);
                if (decisionRef && decisionRef.length > 0) {
                    const handleOverlayClick = () => {
                        const businessRuleTaskData = <JSON><unknown>{
                            "decisionRef": decisionRef,
                            "version": getDecisionVersion(element),
                            "versionTag": getDecisionVersionTag(element),
                            "binding": getDecisionBinding(element),
                        };

                        handleClick(element, businessRuleTaskData);
                    }

                    const tooltipMessage = `${data.tooltipMessage} (${decisionRef})`;
                    const decisionOverlay = createNavigationOverlay({
                        title: tooltipMessage,
                        handleClick: handleOverlayClick
                    });

                    this.overlays.add(element.id, OverlayType.DECISION, decisionOverlay);
                }
            });
        }
    }

    /**
     * Updates visibility for the overlays with the specified type.
     * @param overlayType overlay type
     * @param visible overlay should be visible or not
     */
    public updateOverlaysVisibility(overlayType: OverlayType, visible: boolean) {
        //@ts-ignore
        const overlays: OverlayData[] = this.overlays.get({type: overlayType});

        overlays.forEach(value => {
            value.htmlContainer.style.visibility = visible ? 'visible' : 'hidden';
        });
    }

    /**
     * Adds an overlay with statistics for the specified element.
     * @param data overlay data
     */
    public showActivityStatistics(data: NewActivityStatisticsOverlayData) {
        this.overlays.add(data.elementId, OverlayType.ACTIVITY_STATISTICS, createActivityStatisticsOverlay(data));
    }

    /**
     * Shows the provided activity instance statistics overlays.
     *
     * @param items overlay data for the elements that have statistics
     */
    public setActivityInstanceStatistics(items: ActivityInstanceStatisticsOverlayData[]) {
        this.overlays.remove({type: OverlayType.ACTIVITY_INSTANCE_STATISTICS});

        items.forEach(item => {
            if (!this.elementRegistry.get(item.elementId)) {
                console.error('Element not found:', item.elementId);
                return;
            }
            this.overlays.add(item.elementId, OverlayType.ACTIVITY_INSTANCE_STATISTICS,
                createActivityInstanceStatisticsOverlay(item));
        });
    }

    /**
     * Adds an overlay with variable changes for the specified element.
     * @param data overlay data
     */
    public showVariableChangeOverlay(data: VariableChangeOverlayCmd) {
        this.removeOverlay(data.elementId, OverlayType.VARIABLE_CHANGE);

        const element: ElementLike = this.elementRegistry.get(data.elementId);

        const variableChangeOverlay = createVariableChangeOverlay({
            shape: element,
            changes: data.changes
        });

        this.overlays.add(data.elementId, OverlayType.VARIABLE_CHANGE, variableChangeOverlay);
    }


    /**
     * Removes an overlay with the specified type for the specified element.
     * @param activityId element identifier
     * @param overlayType overlay type
     */
    public removeOverlay(activityId: string, overlayType: string) {
        this.overlays.remove({element: activityId, type: overlayType});
    }

    /**
     * Removes all overlays of the specified type.
     * @param overlayType overlay type
     */
    public removeOverlays(overlayType: string) {
        this.overlays.remove({type: overlayType});
    }

    /**
     * Adds overlays for the transaction boundary (before/after) for all elements.
     */
    public addTransactionBoundaryOverlays() {
        this.overlays.remove({type: OverlayType.TRANSACTION_BOUNDARY});

        const elements: ElementLike[] = this.elementRegistry.filter(element => {
            return element.type !== 'label';
        });

        elements.forEach((shape: ElementLike) => {
            const transactionBoundary: ElementTransactionBoundary = getElementTransactionBoundary(shape);

            if (!transactionBoundary) {
                return;
            }

            const addIncomingTransactionBoundaryOverlay = (type: BeforeElementTransactionType) => {
                const incoming = shape.incoming || [];
                const hasIncoming = incoming.length > 0;

                if (hasIncoming) {
                    this.addOverlayForConnections(shape, transactionBoundary, incoming, true, type);
                } else {
                    // no incoming connection, calculate position in the front
                    this.addTransactionBoundaryOverlay(shape, {
                        x: shape.x,
                        y: shape.y + shape.height / 2
                    }, transactionBoundary, type);
                }
            };

            if (transactionBoundary.engineWaitState) {
                addIncomingTransactionBoundaryOverlay(BeforeElementTransactionType.ENGINE_WAIT_STATE)
            }

            if (transactionBoundary.asyncBefore) {
                addIncomingTransactionBoundaryOverlay(BeforeElementTransactionType.ASYNC_BEFORE);
            }

            if (transactionBoundary.asyncAfter) {
                const outgoing = shape.outgoing || [];
                const hasOutgoing = outgoing.length > 0;

                if (hasOutgoing) {
                    this.addOverlayForConnections(shape, transactionBoundary, outgoing, false);
                } else {
                    // no outgoing connection, calculate position after the element
                    this.addTransactionBoundaryOverlay(shape, {
                        x: shape.x + shape.width,
                        y: shape.y + shape.height / 2
                    }, transactionBoundary);
                }
            }

        });
    }

    /**
     * Adds a transaction boundary overlay to a shape element at a specified waypoint.
     *
     * @param {ElementLike} shape - The shape element to which the transaction boundary overlay will be added.
     * @param {Point} waypoint - The waypoint indicating the location of the transaction boundary.
     * @param {ElementTransactionBoundary} [transactionBoundary] - Optional parameter representing the transaction boundary element.
     * @param {BeforeElementTransactionType} [type] - Required for the transactions before the element.
     */
    private addTransactionBoundaryOverlay = (shape: ElementLike, waypoint: Point,
                                             transactionBoundary?: ElementTransactionBoundary, type?: BeforeElementTransactionType): void => {
        const rect = {...waypoint};
        const overlay = createTransactionBoundaryOverlay({
            shape,
            waypoint: rect,
            transactionBoundary,
            beforeType: type
        });
        this.overlays.add(shape.id, OverlayType.TRANSACTION_BOUNDARY, overlay);
    };

    /**
     * Adds overlays to the provided connections based on transaction boundaries.
     *
     * @param {ElementLike} shape - The visual object to which the overlays will be added.
     * @param {ElementTransactionBoundary} transactionBoundary - The transaction boundary linked to the overlay.
     * @param {any[]} connections - An array of connections to process for adding overlays.
     * @param {boolean} isIncoming - Specifies whether the connections are incoming or outgoing.
     * @return {void} This method does not return a value.
     */
    private addOverlayForConnections(shape: ElementLike, transactionBoundary: ElementTransactionBoundary,
                                     connections: any[], isIncoming: boolean, type?: BeforeElementTransactionType): void {
        forEach(connections, (connection: any) => {
            if (connection.type !== 'bpmn:SequenceFlow') {
                return;
            }

            let waypoint: Rect;
            if (isIncoming) {
                const lastWaypointIndex = connection.waypoints.length - 1;
                waypoint = connection.waypoints[lastWaypointIndex];
            } else {
                waypoint = connection.waypoints[0];
            }
            this.addTransactionBoundaryOverlay(shape, waypoint, transactionBoundary, type);
        });
    }

    public addAnimationOverlay(elementId: string, durationInSeconds: number) {
        const element = this.elementRegistry.get(elementId);
        if (!element) {
            console.error('Element not found:', elementId);
            return;
        }

        const animationOverlayData = createAnimationOverlay(element);

        const overlayId = this.overlays.add(elementId, OverlayType.HIGHLIGHT, animationOverlayData.overlayData);

        setTimeout(() => {
            animationOverlayData.animationContainer.classList.add('bpmn-animation-overlay-fadeout');

            setTimeout(() => {
                this.overlays.remove(overlayId);
            }, 200);
        }, durationInSeconds * 1000);
    }

    private shouldRenderSendMessageOverlay(element: ElementLike, data: SendMessageOverlaysData): boolean {
        if (data.useActiveEvents) {
            const isRunningActivity = this.canvas.hasMarker(element.id, ElementMarkerType.RUNNING_ACTIVITY);
            if (isRunningActivity) {
                return true;

            }
            if (element.host) {
                return this.canvas.hasMarker(element.host, ElementMarkerType.RUNNING_ACTIVITY);
            }

            return false;
        }
        return data.useStartEvents && element.type === "bpmn:StartEvent";
    }
}