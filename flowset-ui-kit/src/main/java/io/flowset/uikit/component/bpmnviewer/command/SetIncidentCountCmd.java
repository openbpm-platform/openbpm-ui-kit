/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

import io.flowset.uikit.component.bpmnviewer.model.ElementIncidentData;

import java.util.ArrayList;
import java.util.List;
/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to show the overlay with incidents count for the process elements.
 */
public class SetIncidentCountCmd {

    protected List<? extends ElementIncidentData> elements = new ArrayList<>();

    /**
     * Creates the command with an empty list of elements.
     */
    public SetIncidentCountCmd() {
    }

    /**
     * Creates the command with the provided list of elements.
     *
     * @param elements a list of elements with their incident counts
     */
    public SetIncidentCountCmd(List<? extends ElementIncidentData> elements) {
        this.elements = elements;
    }

    /**
     * Gets a list of elements the incident count is shown for.
     *
     * @return a list of elements with their incident counts
     */
    public List<? extends ElementIncidentData> getElements() {
        return elements;
    }

    /**
     * Sets a list of elements to show the incident count for.
     *
     * @param elements a list of elements with their incident counts
     */
    public void setElements(List<? extends ElementIncidentData> elements) {
        this.elements = elements;
    }
}
