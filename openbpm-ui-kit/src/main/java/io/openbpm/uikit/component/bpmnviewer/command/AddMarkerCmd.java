/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer} to add a marker to process element.
 */
public class AddMarkerCmd {

    protected String elementId;
    protected String marker;

    public AddMarkerCmd() {
    }

    public AddMarkerCmd(String elementId, String marker) {
        this.elementId = elementId;
        this.marker = marker;
    }

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
