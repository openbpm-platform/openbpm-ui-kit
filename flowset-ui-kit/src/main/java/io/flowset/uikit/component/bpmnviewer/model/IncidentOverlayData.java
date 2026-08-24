/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * Contains the data required to show an incident overlay for the process element in the {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer}.
 */
public class IncidentOverlayData {
    private String elementId;
    private Integer incidentCount;
    private String tooltipMessage;

    /**
     * Creates the overlay data with no attributes set.
     */
    public IncidentOverlayData() {
    }

    /**
     * Creates the overlay data with all the attributes.
     *
     * @param elementId      a process element identifier to show the overlay for
     * @param incidentCount  a count of the opened incidents for the process element
     * @param tooltipMessage a tooltip message to show for the overlay
     */
    public IncidentOverlayData(String elementId, Integer incidentCount, String tooltipMessage) {
        this.elementId = elementId;
        this.incidentCount = incidentCount;
        this.tooltipMessage = tooltipMessage;
    }

    /**
     * Gets a process element identifier the overlay is shown for.
     *
     * @return a process element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets a process element identifier to show the overlay for.
     *
     * @param elementId a process element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a count of the opened incidents for the process element. The value is shown in the overlay as is,
     * without formatting.
     *
     * @return a count of the opened incidents for the process element
     */
    public Integer getIncidentCount() {
        return incidentCount;
    }

    /**
     * Sets a count of the opened incidents for the process element.
     *
     * @param incidentCount a count of the opened incidents for the process element
     */
    public void setIncidentCount(Integer incidentCount) {
        this.incidentCount = incidentCount;
    }

    /**
     * Gets a tooltip message shown for the overlay.
     *
     * @return a tooltip message
     */
    public String getTooltipMessage() {
        return tooltipMessage;
    }

    /**
     * Sets a tooltip message shown for the overlay.
     *
     * @param tooltipMessage a tooltip message
     */
    public void setTooltipMessage(String tooltipMessage) {
        this.tooltipMessage = tooltipMessage;
    }
}
