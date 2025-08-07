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
import io.openbpm.uikit.component.bpmnviewer.model.CallActivityData;

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
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     *                   side, <code>false</code> otherwise
     */
    public CalledProcessOverlayClickEvent(BpmnViewer source, boolean fromClient,
                                          @EventData("event.callActivity") JsonObject callActivity
    ) {
        super(source, fromClient);
        this.callActivity = JsonUtils.readValue(callActivity, new TypeReference<>() {});
    }

    public CallActivityData getCallActivity() {
        return callActivity;
    }
}
