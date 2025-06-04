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
import {
  getBBox
} from 'diagram-js/lib/util/Elements';
import ZoomScroll from 'diagram-js/lib/navigation/zoomscroll/ZoomScroll';
import {
    AddMarkerCmd,
    BpmProcessDefinition,
    IncidentOverlayData,
    ProcessElement,
    RemoveMarkerCmd,
    SetElementColorCmd,
    DecisionInstanceLinkOverlayData
} from "./types";
import {XmlImportCompleteEvent} from "./events";
import BpmDrawing from "./bpm/js/features/bpm-drawing/BpmDrawing";
import BpmnViewer from "./bpm/js/BpmnViewer";
import {ImportParseCompleteEvent} from "bpmn-js/lib/BaseViewer";
import {DecisionInstanceLinkOverlayClickedEvent} from "./events";
import {DocumentationOverlayClickedEvent} from "./events";

@customElement("openbpm-bpmn-viewer")
class OpenBpmBpmnViewer extends LitElement {
    private readonly BPMN_VIEWER_HOLDER: string = "bpmnViewerHolder";
    private shadowRoot: any;

    private readonly viewer: BpmnViewer;
    private bpmnXml: string | undefined;
    private readonly bpmDrawing: BpmDrawing;
    private readonly canvas: Canvas;
    private zoomScroll: ZoomScroll;
    private overlays: Overlays;
    private elementRegistry:any;

    private processDefinitionsJson: string;

    private documentationOverlays: string[] = [];

    static styles = css`
        .running-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
            fill: var(--bpmn-running-activity-color) !important;
        }

        .incident-overlay {
            background-color: var(--bpmn-incident-overlay-bg-color);
            color: var(--bpmn-incident-overlay-text-color);
            border-radius: 50%;
            padding: 0.025em 0.35em;
            line-height: var(--lumo-line-height-xs);
            display: inline-block;
            text-align: center;
            vertical-align: middle;
            font-size: 12px;
            font-weight: bold;
            border: var(--bpmn-incident-overlay-border);
        }

        .decision-instance-link-overlay {
            background-color: var(--bpmn-decision-instance-link-overlay-background);
            cursor: pointer;
            display: flex;
            border-radius: 20%;
            justify-content: center;
            align-items: center;
            width: 1.4em;
            height: 1.4em;
        }

        .documentation-overlay {
            background-color: var(--bpmn-decision-instance-link-overlay-background);
            cursor: pointer;
            display: flex;
            border-radius: 20%;
            justify-content: center;
            align-items: center;
            width: 1.4em;
            height: 1.4em;
        }
    `;

    constructor() {
        super();
        this.viewer = new BpmnViewer();
        this.bpmDrawing = this.viewer.get<BpmDrawing>("bpmDrawing");
        this.canvas = this.viewer.get<Canvas>("canvas");
        this.zoomScroll = this.viewer.get<ZoomScroll>("zoomScroll");
        this.overlays = this.viewer.get<Overlays>("overlays");
        this.elementRegistry = this.viewer.get("elementRegistry");

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
            this.overlays.add(value.elementId, {
                html: `<div class="incident-overlay" title="${value.tooltipMessage}">${value.incidentCount}</div>`,
                position: {
                    right: 10,
                    bottom: 15
                }
            })
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

            const htmlDiv = document.createElement('div');
            htmlDiv.innerHTML = `
            <div class="documentation-overlay" title="${documentationValue}">
                <svg width="16" height="16" viewBox="0 0 16 16" fill="#ff0000" xmlns="http://www.w3.org/2000/svg">

                    <g clip-path="url(#clip0_2388_139)">
                    <path d="M8 0C3.6 0 0 3.6 0 8C0 12.4 3.6 16 8 16C12.4 16 16 12.4 16 8C16 3.6 12.4 0 8 0ZM8.9 13H6.9V11H8.9V13ZM11 8.1C10.6 8.5 10.2 8.7 9.8 8.8C9.2 9.2 9 9 9 10H7C7 8 8.2 7.4 9 7C9.3 6.9 9.5 6.8 9.7 6.6C9.8 6.5 10 6.3 9.8 5.9C9.6 5.4 9 4.9 8.1 4.9C6.7 4.9 6.5 6.1 6.4 6.4L4.4 6.1C4.5 5 5.4 2.9 8 2.9C9.6 2.9 11 3.8 11.6 5.1C12 6.2 11.8 7.3 11 8.1Z" fill="#ffffff"/>
                    </g>
                    <defs>
                    <clipPath id="clip0_2388_139">
                    <rect width="16" height="16" fill="white"/>
                    </clipPath>
                    </defs>


                </svg>
            </div>
            `;

            (function(element:OpenBpmControlBpmViewer, storedDocumentation:string, storedElement:any) {
                htmlDiv.addEventListener('click', (event:MouseEvent) => {
                    element.dispatchEvent(new DocumentationOverlayClickedEvent(
                        storedElement.id, storedElement.type, storedDocumentation));
                });
            })(this, documentationValue, element);

            let position = {right: 10, top: -10};

            if (element.type === 'bpmn:SequenceFlow') {
                let from = element.waypoints.length / 2 - 1;
                let to = from + 1;
                let minX = Math.min(element.waypoints[from].x, element.waypoints[to].x);
                let maxX = Math.max(element.waypoints[from].x, element.waypoints[to].x);
                let minY = Math.min(element.waypoints[from].y, element.waypoints[to].y);
                let maxY = Math.max(element.waypoints[from].y, element.waypoints[to].y);

                let waypointsBox = getBBox(element);

                position = {
                    left: (minX - waypointsBox.x + (maxX - minX) * 0.5 - 8),
                    top: (minY - waypointsBox.y + (maxY - minY) * 0.5 - 8)
                };
            }

            this.awaitRun(() =>
                this.documentationOverlays.push(this.overlays.add(element.id, {
                    html: htmlDiv,
                    position: position
                }
            )));
        };
    }

    public showDecisionInstanceLinkOverlay(cmdJson: any) {
        const cmd:DecisionInstanceLinkOverlayData = JSON.parse(cmdJson);
        let elements = this.elementRegistry.getAll();
        for (let i = 0; i < elements.length; i++) {
            let element = elements[i];
            if (element.type == "bpmn:BusinessRuleTask" && element.id == cmd.activityId) {
                const htmlDiv = document.createElement('div');
                htmlDiv.innerHTML = `
                <div class="decision-instance-link-overlay" title="${cmd.tooltipMessage}">
                    <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M16 7.9L10 3V6C9.5 6 8.9 6 8 6C0 6 0 14 0 14C0 14 1 10 7.8 10C8.9 10 9.6 10 10 10V12.9L16 7.9Z" fill="#ffffff"/>
                    </svg>
                </div>
                `;
                (function(element:OpenBpmControlBpmViewer, decisionInstanceId:string) {
                    htmlDiv.addEventListener('click', (event:MouseEvent) => {
                        element.dispatchEvent(new DecisionInstanceLinkOverlayClickedEvent(decisionInstanceId));
                    });
                })(this, cmd.decisionInstanceId);
                this.awaitRun(() =>
                    this.overlays.add(element.id, {
                        html: htmlDiv,
                        position: {
                            left: -10,
                            bottom: 15
                        }
                    }
                ));
                break;
            }
        };
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