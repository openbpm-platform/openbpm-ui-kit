/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.internal.JacksonUtils;
import io.flowset.uikit.component.bpmnviewer.BpmnViewer;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * An event that is fired when the overlay for navigating to the called process instances is clicked.
 */
@DomEvent(CalledProcessInstanceOverlayClickEvent.EVENT_NAME)
public class CalledProcessInstanceOverlayClickEvent extends ComponentEvent<BpmnViewer> {
    public static final String EVENT_NAME = "called-process-instance-overlay-clicked";

    protected final List<String> processInstanceIds;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     *                   side, <code>false</code> otherwise
     * @param details    event details containing process instance identifiers
     */
    public CalledProcessInstanceOverlayClickEvent(BpmnViewer source, boolean fromClient,
                                                  @EventData("event.details") ObjectNode details
    ) {
        super(source, fromClient);
        this.processInstanceIds = JacksonUtils.readValue(details.get("processInstanceIds").asArray(), new TypeReference<>() {
        });
    }

    /**
     * Gets identifiers of the process instances called by the Call activity element whose overlay has been clicked.
     *
     * @return called process instance identifiers
     */
    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }
}
