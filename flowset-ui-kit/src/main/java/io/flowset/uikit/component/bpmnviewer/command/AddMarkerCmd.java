/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to add a marker to process element.
 */
public class AddMarkerCmd {

    protected String elementId;
    protected String marker;

    /**
     * Creates the command with no attributes set.
     */
    public AddMarkerCmd() {
    }

    /**
     * Creates the command with the provided element identifier and marker name.
     *
     * @param elementId process element identifier
     * @param marker    a marker name to set for the element
     * @see ElementMarkerType
     */
    public AddMarkerCmd(String elementId, String marker) {
        this.elementId = elementId;
        this.marker = marker;
    }

    /**
     * Creates the command with the provided element identifier and marker type.
     *
     * @param elementId process element identifier
     * @param marker    a marker type to set for the element
     */
    public AddMarkerCmd(String elementId, ElementMarkerType marker) {
        this(elementId, marker.getId());
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
     * Sets an identifier of the element for which the marker is set.
     *
     * @param elementId process element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a marker name.
     *
     * @return marker name
     */
    public String getMarker() {
        return marker;
    }

    /**
     * Sets a marker name.
     *
     * @param marker a marker name to set for the element.
     * @see ElementMarkerType
     */
    public void setMarker(String marker) {
        this.marker = marker;
    }
}
