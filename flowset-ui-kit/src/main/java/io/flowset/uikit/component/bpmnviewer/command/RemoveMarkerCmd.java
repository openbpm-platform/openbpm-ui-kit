/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to remove a marker for a process element.
 */
public class RemoveMarkerCmd {

    protected String elementId;
    protected String marker;

    /**
     * Creates the command with no attributes set.
     */
    public RemoveMarkerCmd() {
    }

    /**
     * Creates the command with the provided element identifier and marker type.
     *
     * @param elementId process element identifier
     * @param marker    a marker type to remove for the element
     */
    public RemoveMarkerCmd(String elementId, ElementMarkerType marker) {
        this(elementId, marker.getId());
    }

    /**
     * Creates the command with the provided element identifier and marker name.
     *
     * @param elementId process element identifier
     * @param marker    a marker name to remove for the element
     * @see ElementMarkerType
     */
    public RemoveMarkerCmd(String elementId, String marker) {
        this.elementId = elementId;
        this.marker = marker;
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
     * Sets an identifier of the element for which the marker is removed.
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
     * @param marker a marker name to remove for the element.
     * @see ElementMarkerType
     */
    public void setMarker(String marker) {
        this.marker = marker;
    }
}
