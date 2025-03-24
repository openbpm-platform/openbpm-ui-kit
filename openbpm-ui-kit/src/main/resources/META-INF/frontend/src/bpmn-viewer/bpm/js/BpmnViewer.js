/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

import Viewer from 'bpmn-js/lib/Viewer';
import inherits from 'inherits';

import KeyboardMoveModule from 'diagram-js/lib/navigation/keyboard-move';
import MoveCanvasModule from 'diagram-js/lib/navigation/movecanvas';
import ZoomScrollModule from 'diagram-js/lib/navigation/zoomscroll';
import BpmRenderer from "./features/bpm-renderer";
import BpmDrawing from "./features/bpm-drawing";

/**
 * Custom BPMN viewer with included custom modules.
 * @type {*[]}
 * @private
 */
export default function BpmnViewer(options) {
    Viewer.call(this, options);
}

inherits(BpmnViewer, Viewer);

BpmnViewer.prototype._customModules = [
    BpmDrawing,
    BpmRenderer,
    KeyboardMoveModule,
    MoveCanvasModule,
    ZoomScrollModule
];

BpmnViewer.prototype._modules = [].concat(
    Viewer.prototype._modules,
    BpmnViewer.prototype._customModules
);