/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer} to color a process element.
 */
public class SetElementColorCmd {

    protected String elementId;
    protected String stroke;
    protected String fill;

    public SetElementColorCmd() {
    }

    public SetElementColorCmd(String elementId, String stroke, String fill) {
        this.elementId = elementId;
        this.stroke = stroke;
        this.fill = fill;
    }

    /**
     * Gets a process element identifier.
     *
     * @return process element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets an identifier of the element for which the colors are applied.
     *
     * @param elementId process element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a element border colors.
     *
     * @return a color of the element borders
     */
    public String getStroke() {
        return stroke;
    }

    /**
     * Sets a color of the process element borders. A format should be applied for the <code>stroke</code> CSS attribute.
     * E.g. the value can be in the HEX format (<code>#000000</code>).
     *
     * @param stroke a string containing color for the element borders
     */
    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    /**
     * Gets an element background color.
     *
     * @return a background color of the element content
     */
    public String getFill() {
        return fill;
    }

    /**
     * Sets a background color of the process element. A format should be applied for the <code>fill</code> CSS attribute.
     * E.g. the value can be in the HEX format (<code>#000000</code>).
     *
     * @param fill a string containing  background color of the process element
     */
    public void setFill(String fill) {
        this.fill = fill;
    }
}
