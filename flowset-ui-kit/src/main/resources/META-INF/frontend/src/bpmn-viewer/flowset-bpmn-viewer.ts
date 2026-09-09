/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

// @ts-ignore
import {html, LitElement} from 'lit';
// @ts-ignore
import {customElement} from 'lit/decorators.js';
import Canvas from 'diagram-js/lib/core/Canvas';
import ZoomScroll from 'diagram-js/lib/navigation/zoomscroll/ZoomScroll';
import {
    ActivityData,
    AddMarkerCmd,
    AutoZoomDirection,
    BpmProcessDefinition,
    PassedFlowsCmd,
    SetInteractiveModeCmd,
    RemoveMarkerCmd,
    ScrollToElementCmd,
    SetElementColorCmd,
    ViewerMode
} from "./types";
import {
    BpmnElementClickEvent,
    CalledProcessInstanceOverlayClickEvent,
    CalledProcessOverlayClickEvent,
    DecisionInstanceLinkOverlayClickedEvent,
    DecisionLinkOverlayClickEvent,
    DocumentationOverlayClickedEvent, ElementContextMenuEvent,
    SendMessageOverlayClickEvent,
    XmlImportCompleteEvent
} from "./events";
import BpmDrawing from "./bpm/js/features/bpm-drawing/BpmDrawing";
import BpmnModelColors from "./bpm/js/features/bpmn-model-colors/BpmnModelColors";
import BpmnViewer from "./bpm/js/BpmnViewer";
import {ImportParseCompleteEvent} from "bpmn-js/lib/BaseViewer";
import ElementRegistry from 'diagram-js/lib/core/ElementRegistry';
import EventBus from 'diagram-js/lib/core/EventBus';
import {Element} from 'bpmn-js/lib/model/Types';
import {bpmnViewerStyles} from "./styles/bpmnViewerStyles";
import {OverlayManager} from "./overlay/OverlayManager";
import {ElementLike} from "diagram-js/lib/model/Types";
import {
    ActivityInstanceStatisticsCmdData,
    CalledInstancesOverlayData,
    CalledProcessOverlaysData,
    DecisionInstanceLinkOverlayData,
    DecisionLinkOverlaysData,
    DocumentationOverlayData,
    VariableChangeOverlayCmd,
    IncidentOverlayData,
    NewActivityStatisticsOverlayData,
    OverlayType,
    SendMessageOverlaysData
} from "./overlay/types";
import {findProcessDefinitions} from "./utils/findProcessDefinitions";
import {BpmnElementDataExtractor} from "./element/BpmnElementDataExtractor";
import {createContextMenuEventData} from "./contextmenu/createContextMenuEventData";
import {isMultiInstanceSupported} from "./utils/multiInstanceTaskUtils";
import {getActivityData} from "./utils/elementMetadataUtils";
import {MarkerManager} from "./marker/MarkerManager";

@customElement("flowset-bpmn-viewer")
class FlowsetBpmnViewer extends LitElement {
    private readonly BPMN_VIEWER_HOLDER: string = "bpmnViewerHolder";

    private readonly IGNORED_ACTIVITY_TYPES: string[] = ["bpmn:Participant", "bpmn:SequenceFlow",
        "bpmn:Collaboration", "bpmn:Process", "label", "bpmn:TextAnnotation",
        "bpmn:DataObjectReference", "bpmn:DataStoreReference",
        "bpmn:Group"
    ];

    private shadowRoot: any;

    private readonly viewer: BpmnViewer;
    private bpmnXml: string | undefined;
    private readonly bpmDrawing: BpmDrawing;
    private readonly canvas: Canvas;
    private zoomScroll: ZoomScroll;
    private eventBus: EventBus;
    private overlayManager: OverlayManager;
    private elementDataExtractor: BpmnElementDataExtractor;
    private viewerHolder: HTMLDivElement | undefined;
    private markerManager: MarkerManager;

    private processDefinitionsJson: string = "[]";

    private mode: string;
    private activeElements?: string[];
    private disabledElements?: string[];
    private showNotPassedAsDisabled: boolean = false;
    /** CSS class previewing the pending marker on hovered clickable elements */
    private hoverMarkerClass?: string;
    /** keeps the "not passed as disabled" presentation active in the current interactive session */
    private notPassedAsDisabled: boolean = false;

    private readonly bpmnModelColors: BpmnModelColors;

    static get styles() {
        return bpmnViewerStyles;
    }

    constructor() {
        super();
        this.viewer = new BpmnViewer();
        this.bpmDrawing = this.viewer.get<BpmDrawing>("bpmDrawing");
        this.canvas = this.viewer.get<Canvas>("canvas");
        this.zoomScroll = this.viewer.get<ZoomScroll>("zoomScroll");
        this.eventBus = this.viewer.get<EventBus>("eventBus");
        this.overlayManager = new OverlayManager(this.viewer);
        this.elementDataExtractor = new BpmnElementDataExtractor();
        this.markerManager = new MarkerManager(this.viewer);
        this.bpmnModelColors = this.viewer.get<BpmnModelColors>("bpmnModelColors");

        this.viewer.on("import.parse.complete", (e: ImportParseCompleteEvent) => {
            const processDefinitions: BpmProcessDefinition[] = findProcessDefinitions(e);
            this.processDefinitionsJson = JSON.stringify(processDefinitions);

            const calledReferences = this.elementDataExtractor.getCalledReferences(e.elementsById);
            this.dispatchEvent(new XmlImportCompleteEvent(this.processDefinitionsJson,
                calledReferences.processes, calledReferences.decisions));

            this.resetZoom();
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
            this.overlayManager.showIncidentOverlay(value);
        }));
    }

    public showDocumentationOverlay(cmdJson: string) {
        const cmd: DocumentationOverlayData = JSON.parse(cmdJson);

        const handleOverlayClick = (element: ElementLike, documentationValue: string) => {
            this.dispatchEvent(new DocumentationOverlayClickedEvent(
                element.id, element.type, documentationValue));
        };

        this.awaitRun(() => {
            this.overlayManager.showDocumentationOverlay({data: cmd, handleClick: handleOverlayClick});
        });
    }

    public showDecisionInstanceLinkOverlay(cmdJson: any) {
        const cmd: DecisionInstanceLinkOverlayData = JSON.parse(cmdJson);
        this.awaitRun(() => {
            const handleOverlayClick = (decisionInstanceId: string) => {
                this.dispatchEvent(new DecisionInstanceLinkOverlayClickedEvent(decisionInstanceId));
            };
            this.overlayManager.showDecisionInstanceLinkOverlay({data: cmd, handleClick: handleOverlayClick})
        });
    }

    public setContextMenuEnabled(enabled: boolean) {
        if (enabled) {
            this.viewer.on('element.contextmenu', (event: any) => {
                const elementType = event.element.type;
                if (elementType !== "bpmn:Process") { //TODO:
                    event.originalEvent.preventDefault();
                    const eventDetails = createContextMenuEventData(event);
                    this.dispatchEvent(new ElementContextMenuEvent(eventDetails));
                }
            });
        } else {
            this.viewer.off('element.contextmenu');
        }
    }

    public showVariableChangeOverlay(cmdJson: string) {
        const cmd: VariableChangeOverlayCmd = JSON.parse(cmdJson);

        this.awaitRun(() => {
            this.overlayManager.showVariableChangeOverlay(cmd);
        })
    }

    public removeVariableChangeOverlays() {
        this.awaitRun(() => {
            this.overlayManager.removeOverlays(OverlayType.VARIABLE_CHANGE);
        });
    }

    public showCalledProcessOverlays(cmdJson: string) {
        const cmd: CalledProcessOverlaysData = JSON.parse(cmdJson);

        this.awaitRun(() => {
            const handleOverlayClick = (element: ElementLike, callActivityData: JSON) => {
                this.dispatchEvent(new CalledProcessOverlayClickEvent(element.id, callActivityData));
            }
            this.overlayManager.showCalledProcessOverlays({data: cmd, handleClick: handleOverlayClick});
        });
    }

    public showDecisionLinkOverlays(cmdJson: string) {
        const cmd: DecisionLinkOverlaysData = JSON.parse(cmdJson);

        this.awaitRun(() => {
            const handleOverlayClick = (element: ElementLike, businessRuleTaskData: JSON) => {
                this.dispatchEvent(new DecisionLinkOverlayClickEvent(element.id, businessRuleTaskData));
            }
            this.overlayManager.showDecisionLinkOverlays({data: cmd, handleClick: handleOverlayClick});
        });
    }

    public showCalledInstanceOverlay(cmdJson: string) {
        const cmd: CalledInstancesOverlayData = JSON.parse(cmdJson);

        this.awaitRun(() => {
            const handleOverlayClick = (details: JSON) => {
                this.dispatchEvent(new CalledProcessInstanceOverlayClickEvent(details));
            }

            this.overlayManager.showCalledInstances({data: cmd, handleClick: handleOverlayClick});
        });
    }

    public addTransactionBoundaries() {
        this.awaitRun(() => {
            this.overlayManager.addTransactionBoundaryOverlays();
        });
    }

    public setTransactionBoundariesVisible(visible: boolean) {
        this.awaitRun(() => {
            this.overlayManager.updateOverlaysVisibility(OverlayType.TRANSACTION_BOUNDARY, visible);
        });
    }

    public scrollToElement(cmdJson: string) {
        const data: ScrollToElementCmd = JSON.parse(cmdJson);
        this.awaitRun(() => {
            const padding = {top: 20, right: 20, bottom: 20, left: 20};

            if (data.autoZoom) {
                this.scrollAndAutoZoom(data.elementId, padding, data.autoZoomDirection ?? AutoZoomDirection.CENTER);
            } else {
                this.canvas.scrollToElement(data.elementId, padding);
            }

            if (data.useAnimation) {
                this.overlayManager.addAnimationOverlay(data.elementId, data.durationInSec);
            }
        });
    }

    private scrollAndAutoZoom(elementId: string,
                              padding: { top: number; right: number; bottom: number; left: number },
                              direction: AutoZoomDirection) {
        const COMFORTABLE_ZOOM = 1.0;
        const LEFT_PADDING_PX = 80;

        const elementRegistry: ElementRegistry = this.viewer.get<ElementRegistry>('elementRegistry');
        const element = elementRegistry.get(elementId) as Element | undefined;
        if (!element) {
            return;
        }

        const hasBounds = typeof element.width === 'number' && typeof element.height === 'number';
        if (!hasBounds || this.canvas.zoom() >= COMFORTABLE_ZOOM) {
            this.canvas.scrollToElement(elementId, padding);
            return;
        }

        const outer = this.canvas.viewbox().outer;
        const vbWidth = outer.width / COMFORTABLE_ZOOM;
        const vbHeight = outer.height / COMFORTABLE_ZOOM;
        const cy = element.y + element.height / 2;

        const vbX = direction === AutoZoomDirection.LEFT
            ? element.x - LEFT_PADDING_PX / COMFORTABLE_ZOOM
            : element.x + element.width / 2 - vbWidth / 2;

        this.canvas.viewbox({
            x: vbX,
            y: cy - vbHeight / 2,
            width: vbWidth,
            height: vbHeight,
        });
    }

    public addMarker(cmdJson: string) {
        const cmd: AddMarkerCmd = JSON.parse(cmdJson);
        this.awaitRun(() => {
            this.markerManager.addMarker(cmd.elementId, cmd.marker);
        });
    }

    public removeMarker(cmdJson: string) {
        const cmd: RemoveMarkerCmd = JSON.parse(cmdJson);
        this.awaitRun(() => {
            this.markerManager.removeMarker(cmd.elementId, cmd.marker);
        });
    }

    public showPassedFlows(passedFlowsCmdJson: string) {
        const cmd: PassedFlowsCmd = JSON.parse(passedFlowsCmdJson) || {};
        this.awaitRun(() => {
           this.markerManager.highlightPassedFlows(cmd.finishedActivities ?? [], cmd.runningActivities ?? []);
        });
    }

    public setBpmnModelColorsVisible(visible: boolean) {
        this.awaitRun(() => this.bpmnModelColors.setVisible(visible));
    }

    public resetZoom() {
        this.awaitRun(() => {
            this.canvas.resized();
            this.canvas.zoom('fit-viewport', 'auto');
        });
    }

    public zoomByStep(step: number) {
        this.awaitRun(() => {
            if (!this.viewerHolder) {
                this.viewerHolder = this.shadowRoot.getElementById(this.BPMN_VIEWER_HOLDER) as HTMLDivElement;
            }
            this.zoomScroll.zoom(step, {
                x: this.viewerHolder.offsetWidth / 2,
                y: this.viewerHolder.offsetHeight / 2
            });
        });
    }

    public setMode(mode?: string) {
        this.mode = mode;
        this.hoverMarkerClass = undefined;
        this.notPassedAsDisabled = false;
        if (mode === ViewerMode.Interactive) {
            this.removeElementInteractionListeners();
            this.addElementInteractionListeners((element) => this.isActiveElement(element));
        } else if (!mode || mode === ViewerMode.ReadOnly) {
            this.removeElementInteractionListeners();
        }
        this.awaitRun(() => {
            this.updateNotPassedPresentation();
        });
    }

    // options not present in the payload are reset to their defaults
    public setInteractiveMode(optionsJson?: string) {
        const options: SetInteractiveModeCmd = optionsJson ? JSON.parse(optionsJson) : {};
        this.mode = ViewerMode.Interactive;
        this.activeElements = options.activeElements?.length ? options.activeElements : undefined;
        this.disabledElements = options.disabledElements?.length ? options.disabledElements : undefined;
        this.hoverMarkerClass = options.activeElementMarker ? `${options.activeElementMarker}-hover` : undefined;
        this.notPassedAsDisabled = !!options.notPassedAsDisabled;
        this.removeElementInteractionListeners();
        this.addElementInteractionListeners((element) => this.isActiveElement(element));
        this.awaitRun(() => {
            this.updateNotPassedPresentation();
        });
    }

    public setShowNotPassedAsDisabled(showAsDisabled: boolean) {
        this.showNotPassedAsDisabled = showAsDisabled;
        this.awaitRun(() => this.updateNotPassedPresentation());
    }

    private updateNotPassedPresentation() {
        const active = this.showNotPassedAsDisabled
            && (this.mode !== ViewerMode.Interactive || this.notPassedAsDisabled);
        this.canvas.getContainer().classList.toggle("bpmn-not-passed-as-disabled", active);
    }

    public setActiveElements(activeElements?: string) {
        this.activeElements = activeElements ? JSON.parse(activeElements) as string[] : undefined;
    }

    public setDisabledElements(elements?: string) {
        this.disabledElements = elements ? JSON.parse(elements) as string[] : undefined;
    }

    public setActivityStatisticsVisible(visible: boolean) {
        this.overlayManager.updateOverlaysVisibility(OverlayType.ACTIVITY_STATISTICS, visible);
    }

    public setActivityStatistics(cmdJson: string) {
        const data: NewActivityStatisticsOverlayData = JSON.parse(cmdJson);

        this.awaitRun(() => {
            this.overlayManager.showActivityStatistics(data);
        });
    }

    public removeActivityStatistics(activityId: string) {
        this.awaitRun(() => {
            this.overlayManager.removeOverlay(activityId, OverlayType.ACTIVITY_STATISTICS);
        });
    }

    public setActivityInstanceStatistics(cmdJson: string) {
        const cmd: ActivityInstanceStatisticsCmdData = JSON.parse(cmdJson) || {};
        this.awaitRun(() => {
            this.overlayManager.setActivityInstanceStatistics(cmd.elements || []);
            this.canvas.getContainer().classList.toggle("bpmn-hide-completed-count", !cmd.visible);
        });
    }

    public setCompletedActivityCountVisible(visible: boolean) {
        this.awaitRun(() => {
            this.canvas.getContainer().classList.toggle("bpmn-hide-completed-count", !visible);
        });
    }

    public getActivityById(activityId: string): ActivityData | undefined {
        const elementRegistry: ElementRegistry = this.viewer.get<ElementRegistry>('elementRegistry');
        const element = elementRegistry.get(activityId);
        return getActivityData(element);
    }

    public getActivities(): ActivityData[] {
        const elementRegistry: ElementRegistry = this.viewer.get<ElementRegistry>('elementRegistry');

        const allElements = elementRegistry.filter((e: Element) => {
            return e.type && this.IGNORED_ACTIVITY_TYPES.indexOf(e.type) === -1;
        });

        const activityList: ActivityData[] = allElements.map((e: Element) => {
            return getActivityData(e);
        });

        return activityList;
    }

    public showSendMessageOverlays(cmdJson: string) {
        const cmd: SendMessageOverlaysData = JSON.parse(cmdJson);
        this.awaitRun(() => {
            const handleClick = (details: JSON) => {
                this.dispatchEvent(new SendMessageOverlayClickEvent(details));
            }
            this.overlayManager.showSendMessageOverlays({data: cmd, handleClick});
        });
    }

    private isContextMenuEnabledElement(element) {
        const type: string = element.type;

        const elementId: string = element.id;

        let isConfiguredActive = true; // an element is in manually set active elements
        if (this.activeElements && this.activeElements.length > 0) {
            isConfiguredActive = this.activeElements.indexOf(elementId) !== -1;
        }

        let isConfiguredDisabled = false; // an element is in manually set disabled elements
        if (this.disabledElements && this.disabledElements.length > 0) {
            isConfiguredDisabled = this.disabledElements.indexOf(elementId) !== -1;
        }

        const ignoredByType = type &&  type !== 'bpmn:Process'; // element is ignored by type

        return isConfiguredActive && !isConfiguredDisabled && !ignoredByType;
    }

    private isActiveElement(element) {
        const type: string = element.type;

        const elementId: string = element.id;

        let isConfiguredActive = true; // an element is in manually set active elements
        if (this.activeElements && this.activeElements.length > 0) {
            isConfiguredActive = this.activeElements.indexOf(elementId) !== -1;
        }

        let isConfiguredDisabled = false; // an element is in manually set disabled elements
        if (this.disabledElements && this.disabledElements.length > 0) {
            isConfiguredDisabled = this.disabledElements.indexOf(elementId) !== -1;
        }

        const ignoredByType = type && this.IGNORED_ACTIVITY_TYPES.indexOf(type) !== -1; // element is ignored by type

        return isConfiguredActive && !isConfiguredDisabled && !ignoredByType;
    }

    private addElementInteractionListeners(isElementActive: (element: any) => boolean) {
        this.eventBus.on('element.hover', (event: any) => {
            const elementActive = isElementActive(event.element);
            if (elementActive) {
                this.canvas.addMarker(event.element.id, 'activity-hover');
                if (this.hoverMarkerClass) {
                    this.canvas.addMarker(event.element.id, this.hoverMarkerClass);
                }
            }
        });

        this.eventBus.on('element.out', (event: any) => {
            const hasMarker = this.canvas.hasMarker(event.element.id, 'activity-hover');
            if (hasMarker) { // an active element can become disabled after click, need to remove marker anyway
                this.canvas.removeMarker(event.element.id, 'activity-hover');
            }
            if (this.hoverMarkerClass && this.canvas.hasMarker(event.element.id, this.hoverMarkerClass)) {
                this.canvas.removeMarker(event.element.id, this.hoverMarkerClass);
            }
        });

        this.eventBus.on('element.click', (event: any) => {
            const elementActive = isElementActive(event.element);
            if (elementActive) {
                this.dispatchEvent(new BpmnElementClickEvent(event.element.id, event.element.type,
                    event.element.businessObject?.name, isMultiInstanceSupported(event.element)));
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
