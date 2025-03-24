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
    AddMarkerCmd,
    BpmProcessDefinition,
    IncidentOverlayData,
    ProcessElement,
    RemoveMarkerCmd,
    SetElementColorCmd
} from "./types";
import {XmlImportCompleteEvent} from "./events";
import BpmDrawing from "./bpm/js/features/bpm-drawing/BpmDrawing";
import BpmnViewer from "./bpm/js/BpmnViewer";
import {ImportParseCompleteEvent} from "bpmn-js/lib/BaseViewer";

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

    private processDefinitionsJson: string;

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
    `;

    constructor() {
        super();
        this.viewer = new BpmnViewer();
        this.bpmDrawing = this.viewer.get<BpmDrawing>("bpmDrawing");
        this.canvas = this.viewer.get<Canvas>("canvas");
        this.zoomScroll = this.viewer.get<ZoomScroll>("zoomScroll");
        this.overlays = this.viewer.get<Overlays>("overlays");

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