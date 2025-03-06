/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.model;

/**
 * Contains the data required to show an incident overlay for the process element in the {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer}.
 */
public class IncidentOverlayData {
    private String elementId;
    private Integer incidentCount;
    private String tooltipMessage;

    public IncidentOverlayData() {
    }

    public IncidentOverlayData(String elementId, Integer incidentCount, String tooltipMessage) {
        this.elementId = elementId;
        this.incidentCount = incidentCount;
        this.tooltipMessage = tooltipMessage;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Integer getIncidentCount() {
        return incidentCount;
    }

    public void setIncidentCount(Integer incidentCount) {
        this.incidentCount = incidentCount;
    }

    public String getTooltipMessage() {
        return tooltipMessage;
    }

    public void setTooltipMessage(String tooltipMessage) {
        this.tooltipMessage = tooltipMessage;
    }
}