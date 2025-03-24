/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer} to remove a marker for a process element.
 */
public class RemoveMarkerCmd {

    protected String elementId;
    protected String marker;

    public RemoveMarkerCmd() {
    }

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
