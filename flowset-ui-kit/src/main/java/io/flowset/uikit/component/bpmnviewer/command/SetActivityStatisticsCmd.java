/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to show the overlay with activity statistics.
 */
public class SetActivityStatisticsCmd {
    protected String elementId;
    protected Integer instanceCount;
    protected Integer incidentCount;

    /**
     * Creates the command for the provided diagram element.
     *
     * @param elementId a diagram element identifier to show the statistics for
     */
    public SetActivityStatisticsCmd(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a diagram element identifier the statistics is shown for.
     *
     * @return a diagram element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets a diagram element identifier to show the statistics for.
     *
     * @param elementId a diagram element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a number of the running activity instances. The viewer formats the number before showing it
     * in the overlay, e.g. 1234 is shown as "1.2k".
     *
     * @return a number of the running activity instances
     */
    public Integer getInstanceCount() {
        return instanceCount;
    }

    /**
     * Sets a number of the running activity instances.
     *
     * @param instanceCount a number of the running activity instances
     */
    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }

    /**
     * Gets a number of the activity incidents. The viewer formats the number before showing it
     * in the overlay, e.g. 1234 is shown as "1.2k".
     *
     * @return a number of the activity incidents
     */
    public Integer getIncidentCount() {
        return incidentCount;
    }

    /**
     * Sets a number of the activity incidents.
     *
     * @param incidentCount a number of the activity incidents
     */
    public void setIncidentCount(Integer incidentCount) {
        this.incidentCount = incidentCount;
    }
}
