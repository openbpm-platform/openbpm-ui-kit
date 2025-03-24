/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.model;

/**
 * An API for object providing data about count of open incidents for the process element.
 */
public interface ElementIncidentData {
    /**
     * @return a process element (activity) identifier
     */
    String getElementId();

    /**
     * @return a count of the opened incidents for the process element
     */
    Integer getIncidentCount();
}
