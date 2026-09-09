/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the data required to highlight the sequence flows the process tokens have passed
 * in the {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer}: the finished activity
 * passes in execution order and the currently running activities.
 */
public class PassedFlowsCmdData {
    private List<String> finishedActivities = new ArrayList<>();
    private List<String> runningActivities = new ArrayList<>();

    /**
     * Gets the identifiers of the finished activity passes, ordered by start time,
     * one entry per pass.
     *
     * @return the identifiers of the finished activity passes
     */
    public List<String> getFinishedActivities() {
        return finishedActivities;
    }

    /**
     * Sets the identifiers of the finished activity passes, ordered by start time,
     * one entry per pass.
     *
     * @param finishedActivities the identifiers of the finished activity passes
     */
    public void setFinishedActivities(List<String> finishedActivities) {
        this.finishedActivities = finishedActivities;
    }

    /**
     * Gets the identifiers of the currently running activities, one entry per active token.
     *
     * @return the identifiers of the currently running activities
     */
    public List<String> getRunningActivities() {
        return runningActivities;
    }

    /**
     * Sets the identifiers of the currently running activities, one entry per active token.
     *
     * @param runningActivities the identifiers of the currently running activities
     */
    public void setRunningActivities(List<String> runningActivities) {
        this.runningActivities = runningActivities;
    }
}
