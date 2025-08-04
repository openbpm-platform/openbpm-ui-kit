/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.model;

public class ActivityStatisticsOverlayData {
    protected String elementId;
    protected String instanceCount;
    protected String instanceCountTooltipMessage;
    protected String incidentCount;
    protected String incidentCountTooltipMessage;

    public ActivityStatisticsOverlayData(String elementId, String instanceCount, String incidentCount) {
        this.elementId = elementId;
        this.instanceCount = instanceCount;
        this.incidentCount = incidentCount;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(String instanceCount) {
        this.instanceCount = instanceCount;
    }

    public String getIncidentCount() {
        return incidentCount;
    }

    public void setIncidentCount(String incidentCount) {
        this.incidentCount = incidentCount;
    }

    public String getInstanceCountTooltipMessage() {
        return instanceCountTooltipMessage;
    }

    public void setInstanceCountTooltipMessage(String instanceCountTooltipMessage) {
        this.instanceCountTooltipMessage = instanceCountTooltipMessage;
    }

    public String getIncidentCountTooltipMessage() {
        return incidentCountTooltipMessage;
    }

    public void setIncidentCountTooltipMessage(String incidentCountTooltipMessage) {
        this.incidentCountTooltipMessage = incidentCountTooltipMessage;
    }
}
