/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.internal.JsonUtils;
import elemental.json.JsonObject;
import io.openbpm.uikit.component.bpmnviewer.BpmnViewer;

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
     */
    public CalledProcessInstanceOverlayClickEvent(BpmnViewer source, boolean fromClient,
                                                  @EventData("event.details") JsonObject details
    ) {
        super(source, fromClient);
        this.processInstanceIds = JsonUtils.readValue(details.getArray("processInstanceIds"), new TypeReference<>() {
        });
    }

    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }
}
