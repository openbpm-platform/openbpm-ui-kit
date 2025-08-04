/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

public class SetActivityStatisticsCmd {
    protected String elementId;
    protected Integer instanceCount;
    protected Integer incidentCount;

    public SetActivityStatisticsCmd(String elementId) {
        this.elementId = elementId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }

    public Integer getIncidentCount() {
        return incidentCount;
    }

    public void setIncidentCount(Integer incidentCount) {
        this.incidentCount = incidentCount;
    }
}
