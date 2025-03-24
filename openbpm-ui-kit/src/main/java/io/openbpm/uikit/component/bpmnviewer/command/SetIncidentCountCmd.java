/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

import io.openbpm.uikit.component.bpmnviewer.model.ElementIncidentData;

import java.util.ArrayList;
import java.util.List;
/**
 * A command for {@link io.openbpm.uikit.component.bpmnviewer.BpmnViewer} to show the overlay with incidents count for the process elements.
 */
public class SetIncidentCountCmd {

    protected List<? extends ElementIncidentData> elements = new ArrayList<>();

    public SetIncidentCountCmd() {
    }

    public SetIncidentCountCmd(List<? extends ElementIncidentData> elements) {
        this.elements = elements;
    }

    public List<? extends ElementIncidentData> getElements() {
        return elements;
    }

    public void setElements(List<? extends ElementIncidentData> elements) {
        this.elements = elements;
    }
}
