/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * Contains the data for showing the overlay with the running instances and incidents statistics
 * for the BPMN diagram element.
 */
public class ActivityStatisticsOverlayData {
    protected String elementId;
    protected String instanceCount;
    protected String instanceCountTooltipMessage;
    protected String incidentCount;
    protected String incidentCountTooltipMessage;

    /**
     * Creates the overlay data with the required attributes.
     *
     * @param elementId     a BPMN diagram element identifier to show the overlay for
     * @param instanceCount a formatted number of the running activity instances
     * @param incidentCount a formatted number of the activity incidents
     */
    public ActivityStatisticsOverlayData(String elementId, String instanceCount, String incidentCount) {
        this.elementId = elementId;
        this.instanceCount = instanceCount;
        this.incidentCount = incidentCount;
    }

    /**
     * Gets a BPMN diagram element identifier the overlay is shown for.
     *
     * @return a BPMN diagram element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets a BPMN diagram element identifier to show the overlay for.
     *
     * @param elementId a BPMN diagram element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a formatted number of the running activity instances.
     *
     * @return a formatted number of the running activity instances
     */
    public String getInstanceCount() {
        return instanceCount;
    }

    /**
     * Sets a formatted number of the running activity instances.
     *
     * @param instanceCount a formatted number of the running activity instances
     */
    public void setInstanceCount(String instanceCount) {
        this.instanceCount = instanceCount;
    }

    /**
     * Gets a formatted number of the activity incidents.
     *
     * @return a formatted number of the activity incidents
     */
    public String getIncidentCount() {
        return incidentCount;
    }

    /**
     * Sets a formatted number of the activity incidents.
     *
     * @param incidentCount a formatted number of the activity incidents
     */
    public void setIncidentCount(String incidentCount) {
        this.incidentCount = incidentCount;
    }

    /**
     * Gets a tooltip message shown for the running instances count.
     *
     * @return a tooltip message for the running instances count
     */
    public String getInstanceCountTooltipMessage() {
        return instanceCountTooltipMessage;
    }

    /**
     * Sets a tooltip message shown for the running instances count.
     *
     * @param instanceCountTooltipMessage a tooltip message for the running instances count
     */
    public void setInstanceCountTooltipMessage(String instanceCountTooltipMessage) {
        this.instanceCountTooltipMessage = instanceCountTooltipMessage;
    }

    /**
     * Gets a tooltip message shown for the incidents count.
     *
     * @return a tooltip message for the incidents count
     */
    public String getIncidentCountTooltipMessage() {
        return incidentCountTooltipMessage;
    }

    /**
     * Sets a tooltip message shown for the incidents count.
     *
     * @param incidentCountTooltipMessage a tooltip message for the incidents count
     */
    public void setIncidentCountTooltipMessage(String incidentCountTooltipMessage) {
        this.incidentCountTooltipMessage = incidentCountTooltipMessage;
    }
}
