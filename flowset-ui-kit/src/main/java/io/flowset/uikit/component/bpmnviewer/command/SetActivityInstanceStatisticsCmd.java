/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

import io.flowset.uikit.component.bpmnviewer.model.ActivityInstanceStatisticsData;

import java.util.List;

/**
 * A command to add the activity instance statistics overlays (active, completed and
 * incident counts) for the process elements, replacing the previously shown ones.
 * The completed counts are hidden unless {@link #setVisible(boolean)} is set.
 */
public class SetActivityInstanceStatisticsCmd {

    protected List<? extends ActivityInstanceStatisticsData> elements;

    protected boolean visible = false;

    /**
     * Creates the command with the provided element statistics list.
     *
     * @param elements statistics for the process elements
     */
    public SetActivityInstanceStatisticsCmd(List<? extends ActivityInstanceStatisticsData> elements) {
        this.elements = elements;
    }

    /**
     * Gets statistics for the process elements.
     *
     * @return statistics for the process elements
     */
    public List<? extends ActivityInstanceStatisticsData> getElements() {
        return elements;
    }

    /**
     * Sets statistics for the process elements.
     *
     * @param elements statistics for the process elements
     */
    public void setElements(List<? extends ActivityInstanceStatisticsData> elements) {
        this.elements = elements;
    }

    /**
     * Gets whether the completed activity instance counts should be visible.
     *
     * @return whether the completed counts should be visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets whether the completed activity instance counts should be visible (hidden by default).
     *
     * @param visible whether the completed counts should be visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
