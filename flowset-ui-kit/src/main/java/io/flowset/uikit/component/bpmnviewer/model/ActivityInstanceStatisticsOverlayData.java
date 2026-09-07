/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * Contains the data required to show an activity instance statistics overlay for the process
 * element in the {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer}. Counts are
 * pre-formatted strings; a {@code null} count is not rendered.
 */
public class ActivityInstanceStatisticsOverlayData {
    private String elementId;
    private String activeCount;
    private String completedCount;
    private String incidentCount;
    private String activeCountTooltipMessage;
    private String completedCountTooltipMessage;
    private String incidentCountTooltipMessage;

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
     * Gets a pre-formatted count of the active instances for the process element.
     *
     * @return a pre-formatted count of the active instances, or {@code null} if it is not shown
     */
    public String getActiveCount() {
        return activeCount;
    }

    /**
     * Sets a pre-formatted count of the active instances for the process element.
     *
     * @param activeCount a pre-formatted count of the active instances
     */
    public void setActiveCount(String activeCount) {
        this.activeCount = activeCount;
    }

    /**
     * Gets a pre-formatted count of the completed activity instances for the process element.
     *
     * @return a pre-formatted count of the completed activity instances, or {@code null} if it is not shown
     */
    public String getCompletedCount() {
        return completedCount;
    }

    /**
     * Sets a pre-formatted count of the completed activity instances for the process element.
     *
     * @param completedCount a pre-formatted count of the completed activity instances
     */
    public void setCompletedCount(String completedCount) {
        this.completedCount = completedCount;
    }

    /**
     * Gets a pre-formatted count of the open incidents for the process element.
     *
     * @return a pre-formatted count of the open incidents, or {@code null} if it is not shown
     */
    public String getIncidentCount() {
        return incidentCount;
    }

    /**
     * Sets a pre-formatted count of the open incidents for the process element.
     *
     * @param incidentCount a pre-formatted count of the open incidents
     */
    public void setIncidentCount(String incidentCount) {
        this.incidentCount = incidentCount;
    }

    /**
     * Gets a tooltip message shown for the active token count.
     *
     * @return a tooltip message for the active token count
     */
    public String getActiveCountTooltipMessage() {
        return activeCountTooltipMessage;
    }

    /**
     * Sets a tooltip message to show for the active token count.
     *
     * @param activeCountTooltipMessage a tooltip message for the active token count
     */
    public void setActiveCountTooltipMessage(String activeCountTooltipMessage) {
        this.activeCountTooltipMessage = activeCountTooltipMessage;
    }

    /**
     * Gets a tooltip message shown for the passed (finished) activity instance count.
     *
     * @return a tooltip message for the passed activity instance count
     */
    public String getCompletedCountTooltipMessage() {
        return completedCountTooltipMessage;
    }

    /**
     * Sets a tooltip message to show for the passed (finished) activity instance count.
     *
     * @param completedCountTooltipMessage a tooltip message for the passed activity instance count
     */
    public void setCompletedCountTooltipMessage(String completedCountTooltipMessage) {
        this.completedCountTooltipMessage = completedCountTooltipMessage;
    }

    /**
     * Gets a tooltip message shown for the incident count.
     *
     * @return a tooltip message for the incident count
     */
    public String getIncidentCountTooltipMessage() {
        return incidentCountTooltipMessage;
    }

    /**
     * Sets a tooltip message to show for the incident count.
     *
     * @param incidentCountTooltipMessage a tooltip message for the incident count
     */
    public void setIncidentCountTooltipMessage(String incidentCountTooltipMessage) {
        this.incidentCountTooltipMessage = incidentCountTooltipMessage;
    }
}
