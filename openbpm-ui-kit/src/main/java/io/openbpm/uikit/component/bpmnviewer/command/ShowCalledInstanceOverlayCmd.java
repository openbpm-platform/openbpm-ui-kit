/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

import java.util.List;

/**
 * A command for {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer} to show the overlay for navigating to the called process instances from the Call activity diagram element.
 */
public class ShowCalledInstanceOverlayCmd {
    private String elementId;
    private List<String> processInstanceIds;

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    public void setProcessInstanceIds(List<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }
}
