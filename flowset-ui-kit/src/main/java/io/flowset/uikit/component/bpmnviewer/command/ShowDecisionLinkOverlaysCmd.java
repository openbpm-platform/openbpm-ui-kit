/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

import io.flowset.uikit.component.bpmnviewer.BpmnViewer;

/**
 * A command for {@link BpmnViewer} to show or hide the overlays for navigating to the decision definition from the Business rule task diagram element.
 */
public class ShowDecisionLinkOverlaysCmd {
    protected boolean visible;

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
}
