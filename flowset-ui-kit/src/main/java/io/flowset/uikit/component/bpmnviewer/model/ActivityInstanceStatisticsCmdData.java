/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the data required to show the activity instance statistics overlays in the
 * {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer}: the overlay data for the
 * process elements and whether the completed activity instance counts should be visible.
 */
public class ActivityInstanceStatisticsCmdData {
    private boolean visible;
    private List<ActivityInstanceStatisticsOverlayData> elements = new ArrayList<>();

    /**
     * Gets whether the completed activity instance counts should be visible.
     *
     * @return whether the completed counts should be visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets whether the completed activity instance counts should be visible.
     *
     * @param visible whether the completed counts should be visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets the overlay data for the process elements.
     *
     * @return the overlay data for the process elements
     */
    public List<ActivityInstanceStatisticsOverlayData> getElements() {
        return elements;
    }

    /**
     * Sets the overlay data for the process elements.
     *
     * @param elements the overlay data for the process elements
     */
    public void setElements(List<ActivityInstanceStatisticsOverlayData> elements) {
        this.elements = elements;
    }
}
