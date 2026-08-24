/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * Contains the data required the show/hide the overlays for navigating to the decision from the Business Rule Task element.
 */
public class DecisionLinkOverlayData {
    protected boolean visible;
    protected String tooltipMessage;

    /**
     * Gets whether the overlays should be shown.
     *
     * @return {@code true} if the overlays should be shown, {@code false} otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets whether the overlays should be shown.
     *
     * @param visible {@code true} to show the overlays, {@code false} to hide them
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets a tooltip message shown for the overlays.
     *
     * @return a tooltip message
     */
    public String getTooltipMessage() {
        return tooltipMessage;
    }

    /**
     * Sets a tooltip message shown for the overlays.
     *
     * @param tooltipMessage a tooltip message
     */
    public void setTooltipMessage(String tooltipMessage) {
        this.tooltipMessage = tooltipMessage;
    }
}
