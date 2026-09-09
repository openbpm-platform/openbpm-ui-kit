/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

import BaseRenderer from 'diagram-js/lib/draw/BaseRenderer';
import { black, white } from 'bpmn-js/lib/draw/BpmnRenderUtil';

import inherits from 'inherits-browser';

// Above BpmRenderer (1500) and the default BpmnRenderer (1000), so user-colored
// elements are intercepted first while hiding is active.
const PRIORITY = 2000;

// Override colors passed as render attrs; they take priority over the DI colors
// (see getFillColor/getStrokeColor/getLabelColor in bpmn-js BpmnRenderUtil).
// black/white are the bpmn-js defaults for uncolored elements.
const HIDDEN_COLOR_ATTRS = {
    fill: white,
    stroke: black
};

/**
 * While BPMN model colors are hidden, delegates drawing of user-colored elements to the
 * standard renderers with neutral color overrides, so bpmn-js itself renders them
 * exactly like uncolored elements (including icons, labels and connection markers).
 */
export default function BpmnModelColorsRenderer(eventBus, bpmnRenderer, bpmRenderer, bpmnModelColors) {
    BaseRenderer.call(this, eventBus, PRIORITY);
    this._bpmnRenderer = bpmnRenderer;
    this._bpmRenderer = bpmRenderer;
    this._bpmnModelColors = bpmnModelColors;
}

inherits(BpmnModelColorsRenderer, BaseRenderer);

BpmnModelColorsRenderer.$inject = ['eventBus', 'bpmnRenderer', 'bpmRenderer', 'bpmnModelColors'];

BpmnModelColorsRenderer.prototype.canRender = function (element) {
    return !this._bpmnModelColors.isVisible() && this._bpmnModelColors.isModelColored(element);
};

BpmnModelColorsRenderer.prototype.drawShape = function (parentGfx, element) {
    if (this._bpmRenderer.canRender(element)) {
        return this._bpmRenderer.drawShape(parentGfx, element, HIDDEN_COLOR_ATTRS);
    }
    return this._bpmnRenderer.drawShape(parentGfx, element, HIDDEN_COLOR_ATTRS);
};

BpmnModelColorsRenderer.prototype.drawConnection = function (parentGfx, element) {
    return this._bpmnRenderer.drawConnection(parentGfx, element, HIDDEN_COLOR_ATTRS);
};
