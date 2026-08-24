/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

import java.util.List;

/**
 * Contains the data for showing the overlay for navigating to the called process instances from the Call activity element.
 */
public class CalledInstancesOverlayData {
    private String elementId;
    private List<String> processInstanceIds;
    private String tooltipMessage;

    /**
     * Gets a Call activity element identifier the overlay is shown for.
     *
     * @return a BPMN diagram element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets a Call activity element identifier to show the overlay for.
     *
     * @param elementId a BPMN diagram element identifier
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
