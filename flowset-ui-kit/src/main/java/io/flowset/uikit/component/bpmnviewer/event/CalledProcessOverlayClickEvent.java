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
import io.flowset.uikit.component.bpmnviewer.model.CallActivityData;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.node.ObjectNode;

/**
 * An event that is fired when the overlay for navigating to the called process is clicked.
 */
@DomEvent(CalledProcessOverlayClickEvent.EVENT_NAME)
public class CalledProcessOverlayClickEvent extends ComponentEvent<BpmnViewer> {
    public static final String EVENT_NAME = "called-process-overlay-clicked";

    protected final CallActivityData callActivity;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source       the source component
     * @param fromClient   <code>true</code> if the event originated from the client
     *                     side, <code>false</code> otherwise
     * @param callActivity call activity element data
     */
    public CalledProcessOverlayClickEvent(BpmnViewer source, boolean fromClient,
                                          @EventData("event.callActivity") ObjectNode callActivity
    ) {
        super(source, fromClient);
        this.callActivity = JacksonUtils.readValue(callActivity, new TypeReference<>() {
        });
    }

    /**
     * Gets the data of the Call activity element whose overlay has been clicked.
     *
     * @return call activity element data
     */
    public CallActivityData getCallActivity() {
        return callActivity;
    }
}
