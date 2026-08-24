/*
 * Copyright (c) Haulmont 2024. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.fragment.formviewer;

import com.vaadin.flow.component.html.Div;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.ViewComponent;
import io.flowset.uikit.component.formviewer.FormViewer;

/**
 * A wrapper fragment for displaying {@link FormViewer}.
 */
@FragmentDescriptor("form-viewer-fragment.xml")
public class FormViewerFragment extends Fragment<Div> {

    @ViewComponent
    protected Div viewerContainer;

    protected FormViewer formViewer;

    /**
     * Creates the form viewer component, imports the provided form JSON into it
     * and places the viewer into the fragment.
     *
     * @param bpmnXml form JSON string to show in the viewer
     */
    public void initViewer(String bpmnXml) {
        this.formViewer = uiComponents.create(FormViewer.class);
        this.formViewer.setFormJson(bpmnXml);
        viewerContainer.removeAll();
        viewerContainer.add(formViewer);
    }
}
