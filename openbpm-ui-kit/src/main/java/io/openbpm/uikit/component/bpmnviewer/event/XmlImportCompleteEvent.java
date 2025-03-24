/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.openbpm.uikit.component.bpmnviewer.BpmnViewer;

/**
 * Event that fires after BPMN 2.0 XML is imported in the BPMN viewer.
 */
@DomEvent(XmlImportCompleteEvent.EVENT_NAME)
public class XmlImportCompleteEvent extends ComponentEvent<BpmnViewer> {
    public static final String EVENT_NAME = "xml-import-complete";

    private final String processDefinitionsJson;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source                 the source component
     * @param fromClient             <code>true</code> if the event originated from the client
     *                               side, <code>false</code> otherwise
     * @param processDefinitionsJson imported process definitions in JSON format
     */
    public XmlImportCompleteEvent(BpmnViewer source, boolean fromClient,
                                  @EventData("event.processDefinitionsJson") String processDefinitionsJson) {
        super(source, fromClient);
        this.processDefinitionsJson = processDefinitionsJson;
    }

    public String getProcessDefinitionsJson() {
        return processDefinitionsJson;
    }
}
