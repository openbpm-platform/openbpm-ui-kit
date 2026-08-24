/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.flowset.uikit.component.bpmnviewer.BpmnViewer;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/**
 * An event that is fired when the overlay for sending a message is clicked.
 */
@DomEvent(SendMessageOverlayClickEvent.EVENT_NAME)
public class SendMessageOverlayClickEvent extends ComponentEvent<BpmnViewer> {
    public static final String EVENT_NAME = "send-message-overlay-clicked";

    protected final String messageName;
    protected final String elementType;
    protected final String elementId;
    protected final String elementName;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     *                   side, <code>false</code> otherwise
     * @param details    event details containing the message name and the data of the clicked element
     */
    public SendMessageOverlayClickEvent(BpmnViewer source, boolean fromClient,
                                        @EventData("event.details") JsonNode details) {
        super(source, fromClient);
        this.messageName = details.get("messageName").asString();
        this.elementType = details.get("elementType").asString();
        this.elementId = details.get("elementId").asString();
        this.elementName = getElementName(details);
    }

    /**
     * Gets a name of the message to send, defined for the clicked event element.
     *
     * @return message name
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * Gets a type of the element whose overlay has been clicked, e.g. {@code bpmn:StartEvent}.
     *
     * @return element type
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * Gets an identifier of the element whose overlay has been clicked.
     *
     * @return element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Gets a name of the element whose overlay has been clicked.
     *
     * @return element name or {@code null} if the element has no name
     */
    public String getElementName() {
        return elementName;
    }

    @Nullable
    /**
     * Extracts an element name from the event details, tolerating the missing and {@code null} value.
     *
     * @param details event details received from the client side
     * @return element name or {@code null} if the details contain no name
     */
    protected String getElementName(JsonNode details) {
        if (!details.has("elementName")) {
            return null;
        }

        JsonNode elementNameValue = details.get("elementName");
        return elementNameValue.isNull() ? null : elementNameValue.asString();
    }
}
