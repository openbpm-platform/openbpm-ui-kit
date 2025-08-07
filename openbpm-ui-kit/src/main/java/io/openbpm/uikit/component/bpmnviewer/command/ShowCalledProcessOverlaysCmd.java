/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer} to show or hide the overlays for navigating to the called process from the Call activity diagram element.
 */
public class ShowCalledProcessOverlaysCmd {
    protected boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
