/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

import java.util.List;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to show the overlay for navigating to the called process instances from the Call activity diagram element.
 */
public class ShowCalledInstanceOverlayCmd {
    private String elementId;
    private List<String> processInstanceIds;

    /**
     * Gets a Call activity element identifier the overlay is shown for.
     *
     * @return a diagram element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets a Call activity element identifier to show the overlay for.
     *
     * @param elementId a diagram element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets identifiers of the process instances called by the Call activity element.
     *
     * @return called process instance identifiers
     */
    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    /**
     * Sets identifiers of the process instances called by the Call activity element.
     *
     * @param processInstanceIds called process instance identifiers
     */
    public void setProcessInstanceIds(List<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }
}
