/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer;

import com.vaadin.flow.component.ComponentEventListener;

/**
 * Mode of the {@link BpmnViewer}.
 * The following modes are supported:
 * <ol>
 *     <li><b>Read only:</b> the viewer shows the BPMN diagram and elements on diagram are not clickable. </li>
 *     <li><b>Interactive:</b> the view shows the BPMN diagram and elements are clickable.</li>
 * </ol>
 * @see io.openbpm.uikit.component.bpmnviewer.event.ElementClickEvent
 * @see BpmnViewer#addElementClickListener(ComponentEventListener)
 */
public enum ViewerMode {
    /**
     * In this mode, the BPMN diagram elements are not clickable.
     */
    READ_ONLY,
    /**
     * In this mode, the BPMN diagram elements are clickable.
     */
    INTERACTIVE
}