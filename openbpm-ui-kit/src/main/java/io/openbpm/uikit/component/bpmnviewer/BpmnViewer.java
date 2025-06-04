/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;
import io.jmix.core.Messages;
import io.openbpm.uikit.component.bpmnviewer.command.AddMarkerCmd;
import io.openbpm.uikit.component.bpmnviewer.command.RemoveMarkerCmd;
import io.openbpm.uikit.component.bpmnviewer.command.SetElementColorCmd;
import io.openbpm.uikit.component.bpmnviewer.command.SetIncidentCountCmd;
import io.openbpm.uikit.component.bpmnviewer.command.ShowDecisionInstanceLinkOverlayCmd;
import io.openbpm.uikit.component.bpmnviewer.command.ShowDocumentationOverlayCmd;
import io.openbpm.uikit.component.bpmnviewer.event.DecisionInstanceLinkOverlayClickedEvent;
import io.openbpm.uikit.component.bpmnviewer.event.DocumentationOverlayClickedEvent;
import io.openbpm.uikit.component.bpmnviewer.event.XmlImportCompleteEvent;
import io.openbpm.uikit.component.bpmnviewer.model.IncidentOverlayData;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

@Tag("openbpm-bpmn-viewer")
@NpmPackage(value = "bpmn-js", version = "17.11.1")
@CssImport("bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css")
@CssImport("bpmn-js/dist/assets/bpmn-js.css")
@CssImport("bpmn-js/dist/assets/diagram-js.css")
@CssImport("./styles/bpmn-viewer.css")
@JsModule("./src/bpmn-viewer/openbpm-bpmn-viewer.ts")
public class BpmnViewer extends Component implements HasElement, ApplicationContextAware, InitializingBean {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected String bpmnXml;

    protected ApplicationContext applicationContext;

    protected Messages messages;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initComponent();
    }

    protected void initComponent() {
        this.messages = applicationContext.getBean(Messages.class);
    }

    /**
     * Sets the provided BPMN 2.0 XML to visualize in the viewer.
     *
     * @param bpmnXml a BPMN 2.0 XML to visualize in the viewer
     */
    public void setBpmnXml(String bpmnXml) {
        this.bpmnXml = bpmnXml;
        getElement().callJsFunction("reloadSchema", bpmnXml);
    }

    /**
     * Adds the provided marker to the provided process element.
     *
     * @param cmd a command containing data about process element and marker
     */
    public void addMarker(AddMarkerCmd cmd) {
        callJsEncodedArgumentFunction("addMarker", cmd);
    }

    /**
     * Removes the provided marker for the provided process element.
     *
     * @param cmd a command containing data about process element and marker
     */
    public void removeMarker(RemoveMarkerCmd cmd) {
        callJsEncodedArgumentFunction("removeMarker", cmd);
    }

    /**
     * Shows incident count for the provided elements.
     *
     * @param cmd a command containing data about process elements and incidents
     */
    public void setIncidentCount(SetIncidentCountCmd cmd) {
        List<IncidentOverlayData> items = cmd.getElements()
                .stream()
                .map(entry -> new IncidentOverlayData(entry.getElementId(), entry.getIncidentCount(),
                        messages.formatMessage("", "bpmnViewer.overlays.incidentCount.tooltipMessage", entry.getIncidentCount())))
                .toList();

        callJsEncodedArgumentFunction("setIncidentCount", items);
    }

    /**
     * Colors the provided process element with the provided colors.
     *
     * @param cmd a command containing data about process element and colors
     */
    public void setElementColor(SetElementColorCmd cmd) {
        callJsEncodedArgumentFunction("setElementColor", cmd);
    }

    /**
     * Resets a zoom for the viewer.
     */
    public void resetZoom() {
        getElement().callJsFunction("resetZoom");
    }

    public void showDecisionInstanceLinkOverlay(ShowDecisionInstanceLinkOverlayCmd cmd) {
        callJsEncodedArgumentFunction("showDecisionInstanceLinkOverlay", cmd);
    }

    public void showDocumentationOverlay(ShowDocumentationOverlayCmd cmd) {
        callJsEncodedArgumentFunction("showDocumentationOverlay", cmd);
    }

    /**
     * Registers a component listener for the {@link XmlImportCompleteEvent}.
     *
     * @param listener a component listener for the {@link XmlImportCompleteEvent}
     * @return listener registration
     */
    public Registration addImportCompleteListener(ComponentEventListener<XmlImportCompleteEvent> listener) {
        return addListener(XmlImportCompleteEvent.class, listener);
    }

    /**
     * Registers a component listener for the {@link DecisionInstanceLinkOverlayClickedEvent}.
     *
     * @param listener a component listener for the {@link DecisionInstanceLinkOverlayClickedEvent}
     * @return listener registration
     */
    public Registration addDecisionInstanceLinkOverlayClickListener(
            ComponentEventListener<DecisionInstanceLinkOverlayClickedEvent> listener) {
        return addListener(DecisionInstanceLinkOverlayClickedEvent.class, listener);
    }

    /**
     * Registers a component listener for the {@link DocumentationOverlayClickedEvent}.
     *
     * @param listener a component listener for the {@link DocumentationOverlayClickedEvent}
     * @return listener registration
     */
    public Registration addDocumentationOverlayClickListener(
            ComponentEventListener<DocumentationOverlayClickedEvent> listener) {
        return addListener(DocumentationOverlayClickedEvent.class, listener);
    }

    protected void callJsEncodedArgumentFunction(String cmdName, Object cmd) {
        String encodedCmd;
        try {
            encodedCmd = objectMapper.writeValueAsString(cmd);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        getElement().callJsFunction(cmdName, encodedCmd);
    }
}
