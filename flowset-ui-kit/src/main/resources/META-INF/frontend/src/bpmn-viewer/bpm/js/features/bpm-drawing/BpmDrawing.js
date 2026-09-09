/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

import {getDi} from 'bpmn-js/lib/draw/BpmnRenderUtil';


export default function BpmDrawing(elementRegistry, graphicsFactory, bpmnModelColors) {
    this._elementRegistry = elementRegistry;
    this._graphicsFactory = graphicsFactory;
    this._bpmnModelColors = bpmnModelColors;
}

BpmDrawing.$inject = ['elementRegistry', 'graphicsFactory', 'bpmnModelColors'];

/**
 * Custom function for coloring BPMN element on diagram.
 * @param context
 */
BpmDrawing.prototype.setElementColor = function (context) {
    const elementRegistry = this._elementRegistry;
    const graphicsFactory = this._graphicsFactory;
    const element = this._elementRegistry.get(context.elementId);
    if (!element) {
        return console.error('element not found: ' + context.elementId);
    }

    getDi(element).set('stroke', context.stroke);
    getDi(element).set('fill', context.fill);

    // colors set through the API are highlighting, not diagram colors -
    // keep them visible while user-defined colors are hidden
    this._bpmnModelColors.markProgrammaticallyColored(element);

    const gfx = elementRegistry.getGraphics(element);
    const type = element.waypoints ? 'connection' : 'shape';

    graphicsFactory.update(type, element, gfx);
};