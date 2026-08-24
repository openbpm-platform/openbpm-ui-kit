/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * An API for object providing data about count of open incidents for the process element.
 */
public interface ElementIncidentData {
    /**
     * Gets a process element (activity) identifier.
     *
     * @return a process element (activity) identifier
     */
    String getElementId();

    /**
     * Gets a count of the opened incidents for the process element
     *
     * @return a count of the opened incidents for the process element
     */
    Integer getIncidentCount();
}
