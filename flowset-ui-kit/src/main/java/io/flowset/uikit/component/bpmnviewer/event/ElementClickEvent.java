/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.flowset.uikit.component.bpmnviewer.BpmnViewer;

/**
 * Event that is fired if the element is clicked on the {@link BpmnViewer}.
 * <b>Note: </b> fired only if the {@link BpmnViewer} is in the interactive mode.
 *
 * @see io.flowset.uikit.component.bpmnviewer.ViewerMode
 */
@DomEvent(ElementClickEvent.EVENT_NAME)
public class ElementClickEvent extends ComponentEvent<BpmnViewer> {
    public static final String EVENT_NAME = "bpmn-element-clicked";

    protected String elementId;
    protected String elementType;
    protected String elementName;
    protected boolean isMultiInstance;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source          the source component
     * @param fromClient      <code>true</code> if the event originated from the client
     *                        side, <code>false</code> otherwise
     * @param elementId       clicked element identifier
     * @param elementType     clicked element type
     * @param elementName     clicked element name
     * @param isMultiInstance whether the clicked element is a multi-instance activity
     */
    public ElementClickEvent(BpmnViewer source, boolean fromClient,
                             @EventData("event.elementId") String elementId,
                             @EventData("event.elementType") String elementType,
                             @EventData("event.elementName") String elementName,
                             @EventData("event.isMultiInstance") boolean isMultiInstance) {
        super(source, fromClient);
        this.elementId = elementId;
        this.elementType = elementType;
        this.elementName = elementName;
        this.isMultiInstance = isMultiInstance;
    }

    /**
     * Gets an identifier of the clicked element.
     *
     * @return element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets an identifier of the clicked element.
     *
     * @param elementId element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Gets a type of the clicked element, e.g. {@code bpmn:UserTask}.
     *
     * @return element type
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * Sets a type of the clicked element.
     *
     * @param elementType element type
     */
    public void setElementType(String elementType) {
        this.elementType = elementType;
    }


    /**
     * Gets a name of the clicked element.
     *
     * @return element name or {@code null} if the element has no name
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Sets a name of the clicked element.
     *
     * @param elementName element name
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * Gets whether the clicked element is a multi-instance activity, i.e. the element has
     * the {@code bpmn:MultiInstanceLoopCharacteristics} loop characteristics defined.
     *
     * @return {@code true} if the element is a multi-instance activity, {@code false} otherwise
     */
    public boolean isMultiInstance() {
        return isMultiInstance;
    }

    /**
     * Sets whether the clicked element is a multi-instance activity.
     *
     * @param isMultiInstance whether the element is a multi-instance activity
     */
    public void setMultiInstance(boolean isMultiInstance) {
        this.isMultiInstance = isMultiInstance;
    }

}
