/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

// @ts-ignore
import {css, html, LitElement} from 'lit';
// @ts-ignore
import {customElement} from 'lit/decorators.js';
import Canvas from 'diagram-js/lib/core/Canvas';
import Overlays from "diagram-js/lib/features/overlays/Overlays";
import ZoomScroll from 'diagram-js/lib/navigation/zoomscroll/ZoomScroll';
import {
    ActivityData,
    ActivityStatisticsOverlayData,
    AddMarkerCmd,
    BpmProcessDefinition,
    DecisionInstanceLinkOverlayData,
    IncidentOverlayData,
    OverlayData,
    OverlayType,
    ProcessElement,
    RemoveMarkerCmd,
    SetElementColorCmd,
    ViewerMode
} from "./types";
import {
    BpmnElementClickEvent,
    CalledProcessInstanceOverlayClickEvent,
    CalledProcessOverlayClickEvent,
    DecisionInstanceLinkOverlayClickedEvent,
    DocumentationOverlayClickedEvent,
    XmlImportCompleteEvent
} from "./events";
import BpmDrawing from "./bpm/js/features/bpm-drawing/BpmDrawing";
import BpmnViewer from "./bpm/js/BpmnViewer";
import {ImportParseCompleteEvent} from "bpmn-js/lib/BaseViewer";
import ElementRegistry from 'diagram-js/lib/core/ElementRegistry';
import EventBus from 'diagram-js/lib/core/EventBus';
import {Element} from 'bpmn-js/lib/model/Types';
import {createDocumentationOverlay, createNavigationOverlay} from "./utils/createOverlays";
import {getBinding, getCalledElement, getVersion, getVersionTag} from "./utils/callActivityUtils";

@customElement("openbpm-bpmn-viewer")
class OpenBpmBpmnViewer extends LitElement {
    private readonly BPMN_VIEWER_HOLDER: string = "bpmnViewerHolder";

    private readonly IGNORED_ACTIVITY_TYPES: string[] = ["bpmn:Participant", "bpmn:SequenceFlow", "bpmn:Collaboration", "bpmn:Process"];

    private shadowRoot: any;

    private readonly viewer: BpmnViewer;
    private bpmnXml: string | undefined;
    private readonly bpmDrawing: BpmDrawing;
    private readonly canvas: Canvas;
    private zoomScroll: ZoomScroll;
    private overlays: Overlays;
    private elementRegistry: ElementRegistry;
    private eventBus: EventBus;

    private processDefinitionsJson: string;

    private documentationOverlays: string[] = [];

    private mode: string;

    static styles = css`
        .running-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
            fill: var(--bpmn-running-activity-color) !important;
        }

        .modification-source-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
            stroke: var(--bpmn-modification-source-activity-stroke-color) !important;
            fill: var(--bpmn-modification-source-activity-bg-color) !important;
        }

        .modification-target-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
            stroke: var(--bpmn-modification-target-activity-stroke-color) !important;
            fill: var(--bpmn-modification-target-activity-bg-color) !important;
        }

        .activity-hover:not(.djs-connection) .djs-visual > :nth-child(1) {
            stroke: var(--bpmn-activity-hover-stroke-color) !important;
            fill: var(--bpmn-activity-hover-fill-color) !important;
            cursor: pointer;
        }

        .primary-color-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
            stroke: var(--lumo-primary-color) !important;
            fill: var(--lumo-primary-color-10pct) !important;
        }

        .activity-statistics-overlay {
            display: flex;
            flex-direction: row;
            gap: var(--lumo-space-xs);
        }

        .running-instances-overlay {
            background-color: var(--bpmn-running-instances-overlay-bg-color);
            color: var(--bpmn-running-instances-overlay-text-color);
            border-radius: var(--lumo-border-radius-m);
            line-height: var(--default-bpmn-element-overlay-size);
            padding: 0.1em;
            text-align: center;
            vertical-align: middle;
            font-size: var(--default-bpmn-element-overlay-font-size);
            font-weight: bold;
            border: var(--bpmn-running-instances-overlay-border);
            min-width: var(--default-bpmn-element-overlay-size);
            height: var(--default-bpmn-element-overlay-size);
            display: flex;
            justify-content: center;
        }

        .overlay-hidden {
            display: none;
        }

        .incident-overlay {
            background-color: var(--bpmn-incident-overlay-bg-color);
            color: var(--bpmn-incident-overlay-text-color);
            border-radius: var(--lumo-border-radius-m);
            line-height: var(--default-bpmn-element-overlay-size);
            padding: 0.1em;
            text-align: center;
            vertical-align: middle;
            font-size: var(--default-bpmn-element-overlay-font-size);
            font-weight: bold;
            border: var(--bpmn-incident-overlay-border);
            min-width: var(--default-bpmn-element-overlay-size);
            height: var(--default-bpmn-element-overlay-size);
            display: flex;
            justify-content: center;
        }

        .navigation-overlay {
            background-color: var(--bpmn-navigation-overlay-background);
            cursor: pointer;
            display: flex;
            border-radius: var(--lumo-border-radius-m);
            justify-content: center;
            align-items: center;
            width: calc(var(--default-bpmn-element-overlay-size) + 2px);
            height: calc(var(--default-bpmn-element-overlay-size) + 2px);
        }

        .documentation-overlay {
            background-color: var(--bpmn-navigation-overlay-background);
            cursor: pointer;
            display: flex;
            border-radius: var(--lumo-border-radius-m);
            justify-content: center;
            align-items: center;
            width: calc(var(--default-bpmn-element-overlay-size) + 2px);
            height: calc(var(--default-bpmn-element-overlay-size) + 2px);
        }
    `;

    constructor() {
        super();
        this.viewer = new BpmnViewer();
        this.bpmDrawing = this.viewer.get<BpmDrawing>("bpmDrawing");
        this.canvas = this.viewer.get<Canvas>("canvas");
        this.zoomScroll = this.viewer.get<ZoomScroll>("zoomScroll");
        this.overlays = this.viewer.get<Overlays>("overlays");
        this.elementRegistry = this.viewer.get<ElementRegistry>("elementRegistry");
        this.eventBus = this.viewer.get<EventBus>("eventBus");

        this.viewer.on("import.parse.complete", (e: ImportParseCompleteEvent) => {
            const rootElements = e.definitions?.rootElements as Array<ProcessElement> || [];
            const processDefinitions: BpmProcessDefinition[] = [];
            rootElements.forEach((item: ProcessElement) => {
                if (item.$type === "bpmn:Process") {
                    processDefinitions.push({
                        key: item.id,
                        name: item.name
                    });
                }
            });
            this.processDefinitionsJson = JSON.stringify(processDefinitions);
            this.dispatchEvent(new XmlImportCompleteEvent(this.processDefinitionsJson));
        });
    }

    static get properties() {
        return {
            bpmnXml: {type: String}
        }
    }

    render() {
        return html`
            <div id="${(this.BPMN_VIEWER_HOLDER)}" style="width: 100%; height: 100%"/>
        `;
    }

    updated(updatedProps: any) {
        this.awaitRun(() => this.initViewer());
    }

    public async reloadSchema(xmlSchema: string) {
        await this.viewer.importXML(xmlSchema)
    }

    public setElementColor(cmdJson: string) {
        const cmd: SetElementColorCmd = JSON.parse(cmdJson);
        this.awaitRun(() => this.bpmDrawing.setElementColor(cmd));
    }

    public setIncidentCount(cmdJson: string) {
        const elements: IncidentOverlayData[] = JSON.parse(cmdJson) || [];
        this.awaitRun(() => elements.forEach(value => {
            this.overlays.add(value.elementId, OverlayType.INCIDENT_COUNT, {
                html: `<div class="incident-overlay" title="${value.tooltipMessage}">${value.incidentCount}</div>`,
                position: {
                    left: -10,
                    bottom: 15
                }
            });
        }));
    }

    public showDocumentationOverlay(cmdJson: string) {
        const cmd = JSON.parse(cmdJson);
        if (!cmd.showDocumentationOverlay) {
            this.documentationOverlays.forEach(overlayId => {
                this.overlays.remove(overlayId);
            });
            this.documentationOverlays = [];
            return;
        }
        if (this.documentationOverlays.length > 0) {
            return;
        }

        let elements = this.elementRegistry.getAll();
        for (let i = 0; i < elements.length; i++) {
            let element = elements[i];
            if (element.type === 'label') {
                continue;
            }
            let businessObject = element.businessObject;
            let documentations = businessObject.get('documentation');

            let documentationValue = null;
            documentations.forEach((documentation) => {
                if (documentation.textFormat === "text/plain" && documentation.text
                    && documentation.text.length > 0) {

                    documentationValue = documentation.text;
                }
            });
            if (!documentationValue) {
                continue;
            }

            const handleClick = () => {
                this.dispatchEvent(new DocumentationOverlayClickedEvent(
                    element.id, element.type, documentationValue));
            };

            const documentationOverlay = createDocumentationOverlay(element, documentationValue,
                handleClick);


            this.awaitRun(() =>
                this.documentationOverlays.push(this.overlays.add(element.id, OverlayType.DOCUMENTATION, documentationOverlay)));
        }

    }

    public showDecisionInstanceLinkOverlay(cmdJson: any) {
        const cmd: DecisionInstanceLinkOverlayData = JSON.parse(cmdJson);
        let elements = this.elementRegistry.getAll();
        for (let i = 0; i < elements.length; i++) {
            let element = elements[i];
            if (element.type == "bpmn:BusinessRuleTask" && element.id == cmd.activityId) {
                const handleClick = () => {
                    this.dispatchEvent(new DecisionInstanceLinkOverlayClickedEvent(cmd.decisionInstanceId));
                }
                const decisionInstanceOverlay = createNavigationOverlay(cmd.tooltipMessage,
                    handleClick);

                this.awaitRun(() =>
                    this.overlays.add(element.id, OverlayType.DECISION_INSTANCE, decisionInstanceOverlay));
                break;
            }
        }
    }

    public showCalledProcessOverlays(cmdJson: string) {
        const cmd: { visible?: boolean; tooltipMessage?: string } = JSON.parse(cmdJson);
        let elements = this.elementRegistry.getAll();

        this.overlays.remove({type: OverlayType.CALLED_PROCESS});

        if (cmd.visible) {
            for (let i = 0; i < elements.length; i++) {
                let element = elements[i];
                const calledElement = getCalledElement(element);
                if (element.type == "bpmn:CallActivity" && calledElement && calledElement.length > 0) {
                    const handleClick = () => {
                        const callActivityData = <JSON><unknown>{
                            "calledElement": calledElement,
                            "version": getVersion(element),
                            "versionTag": getVersionTag(element),
                            "binding": getBinding(element),
                        };

                        this.dispatchEvent(new CalledProcessOverlayClickEvent(element.id, callActivityData));
                    }

                    const tooltipMessage = `${cmd.tooltipMessage} (${calledElement})`;
                    const calledProcessOverlay = createNavigationOverlay(tooltipMessage,
                        handleClick);

                    this.awaitRun(() =>
                        this.overlays.add(element.id, OverlayType.CALLED_PROCESS, calledProcessOverlay));
                }
            }
        }
    }

    public showCalledInstanceOverlay(cmdJson: string) {
        const cmd: { elementId: string, processInstanceIds?: string[], tooltipMessage: string } = JSON.parse(cmdJson);
        const handleClick = () => {
            const details = <JSON><unknown>{
                "processInstanceIds": cmd.processInstanceIds
            }
            this.dispatchEvent(new CalledProcessInstanceOverlayClickEvent(details));
        }

        const calledInstancesOverlay = createNavigationOverlay(cmd.tooltipMessage,
            handleClick);

        this.awaitRun(() =>
            this.overlays.add(cmd.elementId, OverlayType.CALLED_PROCESS_INSTANCE, calledInstancesOverlay));
    }

    public addMarker(cmdJson: string) {
        const cmd: AddMarkerCmd = JSON.parse(cmdJson);
        this.awaitRun(() => this.canvas.addMarker(cmd.elementId, cmd.marker));
    }

    public removeMarker(cmdJson: string) {
        const cmd: RemoveMarkerCmd = JSON.parse(cmdJson);
        this.awaitRun(() => this.canvas.removeMarker(cmd.elementId, cmd.marker));
    }

    public resetZoom() {
        this.awaitRun(() => this.zoomScroll.reset());
    }

    public setMode(mode?: string) {
        this.mode = mode;
        if (mode === ViewerMode.Interactive) {
            const ignoredTypes: string[] = ["bpmn:Participant", "bpmn:SequenceFlow", "bpmn:Collaboration", "bpmn:Process"];
            const isActiveElement = element => {
                const type: string | undefined = element.type;
                return type && ignoredTypes.indexOf(type) === -1;
            }
            this.addElementInteractionListeners(isActiveElement)

        } else if (!mode || mode === ViewerMode.ReadOnly) {
            this.removeElementInteractionListeners();
        }
    }

    public setActiveElements(activeElements?: string[]) {
        if (this.mode == ViewerMode.Interactive && activeElements?.length > 0) {
            const isActiveElement = element => activeElements.indexOf(element.id) !== -1

            this.removeElementInteractionListeners();
            this.addElementInteractionListeners(isActiveElement);
        }
    }

    public setActivityStatisticsVisible(visible: boolean) {
        const statOverlay: OverlayData[] = this.overlays.get({type: OverlayType.ACTIVITY_STATISTICS});

        statOverlay.forEach(value => {
            value.htmlContainer.style.visibility = visible ? 'visible' : 'hidden';
        });
    }

    public setActivityStatistics(cmdJson: string) {
        const element: ActivityStatisticsOverlayData = JSON.parse(cmdJson);
        const incidentClassName = element.incidentCount ? 'incident-overlay' : 'overlay-hidden';

        this.awaitRun(() => {
            this.overlays.add(element.elementId, OverlayType.ACTIVITY_STATISTICS, {
                html: `<div class="activity-statistics-overlay">
                   <span class="running-instances-overlay" title="${element.instanceCountTooltipMessage}">${element.instanceCount}</span>
                   <span class="${incidentClassName}" title="${element.incidentCountTooltipMessage}">${element.incidentCount}</span>
                </div>`,
                position: {
                    left: -10,
                    bottom: 15
                }
            });
        });
    }

    public getActivities(): ActivityData[] {
        const elementRegistry: ElementRegistry = this.viewer.get<ElementRegistry>('elementRegistry');

        const allElements = elementRegistry.filter((e: Element) => {
            return e.type && this.IGNORED_ACTIVITY_TYPES.indexOf(e.type) === -1;
        });

        const activityList: ActivityData[] = allElements.map((e: Element) => {
            return {
                id: e.id,
                name: e.businessObject.name,
                type: e.type
            } as ActivityData;
        });

        return activityList;
    }

    private addElementInteractionListeners(isElementActive: (element: any) => boolean) {
        this.eventBus.on('element.hover', (event: any) => {
            const elementActive = isElementActive(event.element);
            if (elementActive) {
                this.canvas.addMarker(event.element.id, 'activity-hover');
            }
        });

        this.eventBus.on('element.out', (event: any) => {
            const elementActive = isElementActive(event.element);
            if (elementActive) {
                this.canvas.removeMarker(event.element.id, 'activity-hover');
            }
        });

        this.eventBus.on('element.click', (event: any) => {
            const elementActive = isElementActive(event.element);
            if (elementActive) {
                this.dispatchEvent(new BpmnElementClickEvent(event.element.id, event.element.type, event.element.businessObject?.name));
            }
        });
    }

    private removeElementInteractionListeners() {
        this.eventBus.off('element.hover');
        this.eventBus.off('element.out');
        this.eventBus.off('element.click');
    }

    private initViewer() {
        this.viewer.attachTo(this.shadowRoot.getElementById(this.BPMN_VIEWER_HOLDER)!);
        if (this.bpmnXml) {
            this.viewer.importXML(this.bpmnXml).then(r => {
                console.log(r.warnings);
            });
        }
    }

    private awaitRun(callable: () => void) {
        setTimeout(() => {
            try {
                callable();
            } catch (err) {
                console.log('error while running callable', err);
            }
        }, 100);
    }
}