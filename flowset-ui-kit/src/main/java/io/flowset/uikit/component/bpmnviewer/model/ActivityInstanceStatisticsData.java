/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * An API for an object providing the activity instance statistics of a process instance
 * for a single process element: counts of active, finished activity
 * instances and open incidents.
 */
public interface ActivityInstanceStatisticsData {

    /**
     * Gets a process element (activity) identifier.
     *
     * @return a process element (activity) identifier
     */
    String getElementId();

    /**
     * Gets a count of the active tokens (active activity instances) for the process element.
     *
     * @return a count of the active tokens, or {@code null} if there are none
     */
    Integer getActiveCount();

    /**
     * Gets a count of the completed activity instances for the process element.
     *
     * @return a count of the completed activity instances, or {@code null} if there are none
     */
    Integer getCompletedCount();

    /**
     * Gets a count of the open incidents for the process element.
     *
     * @return a count of the open incidents, or {@code null} if there are none
     */
    Integer getIncidentCount();
}
