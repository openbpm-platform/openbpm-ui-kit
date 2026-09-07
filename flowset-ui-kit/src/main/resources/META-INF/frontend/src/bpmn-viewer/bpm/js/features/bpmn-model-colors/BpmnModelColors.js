/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

import {getDi} from 'bpmn-js/lib/draw/BpmnRenderUtil';

/**
 * Owns the visibility state of user-defined diagram colors (bioc/color DI namespaces).
 * Toggling re-renders the affected elements, so the BpmnModelColorsRenderer can draw them
 * with neutral colors while hidden. Colors applied at runtime through the public
 * setElementColor API are excluded and stay visible.
 */
export default function BpmnModelColors(elementRegistry, graphicsFactory) {
    this._elementRegistry = elementRegistry;
    this._graphicsFactory = graphicsFactory;
    this._visible = true;
    this._programmaticallyColored = new WeakSet();
}

BpmnModelColors.$inject = ['elementRegistry', 'graphicsFactory'];

BpmnModelColors.prototype.isVisible = function () {
    return this._visible;
};

BpmnModelColors.prototype.setVisible = function (visible) {
    if (this._visible === visible) {
        return;
    }
    this._visible = visible;

    const elementRegistry = this._elementRegistry;
    const graphicsFactory = this._graphicsFactory;
    const self = this;

    elementRegistry.forEach(function (element, gfx) {
        if (!gfx || !self.isModelColored(element)) {
            return;
        }
        const type = element.waypoints ? 'connection' : 'shape';
        graphicsFactory.update(type, element, gfx);
    });
};

/**
 * An element carries BPMN model colors when its DI has bioc/color values and the colors
 * were not applied through the setElementColor API.
 */
BpmnModelColors.prototype.isModelColored = function (element) {
    if (this._programmaticallyColored.has(element)) {
        return false;
    }
    const di = getDi(element);
    if (!di) {
        return false;
    }
    const label = di.get('label');
    return !!(di.get('bioc:stroke') || di.get('bioc:fill')
        || di.get('color:border-color') || di.get('color:background-color')
        || (label && label.get('color:color')));
};

/**
 * Excludes an element from model-color hiding after its colors were set through the API.
 */
BpmnModelColors.prototype.markProgrammaticallyColored = function (element) {
    this._programmaticallyColored.add(element);
};
