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
import io.flowset.uikit.component.bpmnviewer.model.BusinessRuleTaskData;
import io.flowset.uikit.component.bpmnviewer.model.CallActivityData;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.node.ArrayNode;

import java.util.List;

/**
 * Event that fires after BPMN 2.0 XML is imported in the BPMN viewer.
 * <p>
 * The event is fired on every import, i.e. every time a new XML is set to the viewer.
 */
@DomEvent(XmlImportCompleteEvent.EVENT_NAME)
public class XmlImportCompleteEvent extends ComponentEvent<BpmnViewer> {
    public static final String EVENT_NAME = "xml-import-complete";

    protected final String processDefinitionsJson;
    protected final List<CallActivityData> calledProcesses;
    protected final List<BusinessRuleTaskData> calledDecisions;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source                 the source component
     * @param fromClient             <code>true</code> if the event originated from the client
     *                               side, <code>false</code> otherwise
     * @param processDefinitionsJson imported process definitions in JSON format
     * @param calledProcesses        called process references
     * @param calledDecisions        called decision references
     */
    public XmlImportCompleteEvent(BpmnViewer source, boolean fromClient,
                                  @EventData("event.processDefinitionsJson") String processDefinitionsJson,
                                  @EventData("event.calledProcesses") ArrayNode calledProcesses,
                                  @EventData("event.calledDecisions") ArrayNode calledDecisions) {
        super(source, fromClient);
        this.processDefinitionsJson = processDefinitionsJson;
        this.calledProcesses = JacksonUtils.readValue(calledProcesses, new TypeReference<>() {
        });
        this.calledDecisions = JacksonUtils.readValue(calledDecisions, new TypeReference<>() {
        });
    }

    /**
     * Gets information about the process definitions found in the imported BPMN XML.
     * The JSON contains an array of objects with the {@code key} and {@code name} attributes
     * for every process definition of the XML.
     *
     * @return imported process definitions in JSON format
     */
    public String getProcessDefinitionsJson() {
        return processDefinitionsJson;
    }

    /**
     * Gets the data of all the Call activity elements found in the imported BPMN XML.
     *
     * @return called process references
     */
    public List<CallActivityData> getCalledProcesses() {
        return calledProcesses;
    }

    /**
     * Gets the data of all the Business Rule Task elements found in the imported BPMN XML.
     *
     * @return called decision references
     */
    public List<BusinessRuleTaskData> getCalledDecisions() {
        return calledDecisions;
    }
}
