/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer;

import com.vaadin.flow.internal.JacksonUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.internal.DeadlockDetectingCompletableFuture;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import io.flowset.uikit.component.bpmnviewer.command.*;
import io.flowset.uikit.component.bpmnviewer.event.*;
import io.flowset.uikit.component.bpmnviewer.model.*;
import io.jmix.core.Messages;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.json.JsonMapper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * Integrated <code>bpmn-js</code> viewer for viewing BPMN 2.0 process diagrams.
 * <p>
 * Depending on the {@link ViewerMode} set by {@link #setMode(ViewerMode)}, the diagram is either
 * read-only or interactive, i.e. its elements can be clicked.
 */
@Tag("flowset-bpmn-viewer")
@NpmPackage(value = "bpmn-js", version = "18.24.0")
@CssImport("bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css")
@CssImport("bpmn-js/dist/assets/bpmn-js.css")
@CssImport("bpmn-js/dist/assets/diagram-js.css")
@CssImport("./styles/bpmn-viewer.css")
@JsModule("./src/bpmn-viewer/flowset-bpmn-viewer.ts")
public class BpmnViewer extends Component implements HasElement, ApplicationContextAware, InitializingBean {
    private static final DecimalFormat thousandFormat = new DecimalFormat("#.#k", new DecimalFormatSymbols(Locale.US));
    private static final DecimalFormat millionFormat = new DecimalFormat("#.#M", new DecimalFormatSymbols(Locale.US));

    private final JsonMapper objectMapper = new JsonMapper();

    protected String bpmnXml;

    protected ApplicationContext applicationContext;

    protected Messages messages;
    protected ViewerMode mode;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initComponent();
    }

    /**
     * Initializes the component beans. Invoked by Spring after the component properties are set.
     */
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
     * Sets a list of clickable elements on the diagram in the {@link ViewerMode#INTERACTIVE} mode.
     *
     * @param activeElements a list of element identifiers that can be clicked on the diagram.
     */
    public void setActiveElements(@Nullable Collection<String> activeElements) {
        callJsEncodedArgumentFunction("setActiveElements", CollectionUtils.emptyIfNull(activeElements));
    }

    /**
     * Resets zoom for the viewer.
     */
    public void resetZoom() {
        getElement().callJsFunction("resetZoom");
    }

    /**
     * Zooms the viewer by a specified step.
     *
     * @param step the zoom step, positive for zooming in, negative for zooming out
     */
    public void zoomByStep(double step) {
        getElement().callJsFunction("zoomByStep", step);
    }

    /**
     * Sets a list of elements that should be disabled in the viewer interactive mode.
     *
     * @param disabledElements disabled elements
     */
    public void setDisabledElements(@Nullable Collection<String> disabledElements) {
        callJsEncodedArgumentFunction("setDisabledElements", CollectionUtils.emptyIfNull(disabledElements));
    }

    /**
     * Shows the overlay for the specified diagram element to navigate to the evaluated decision instance.
     *
     * @param cmd a command containing the diagram element and decision instance identifiers
     */
    public void showDecisionInstanceLinkOverlay(ShowDecisionInstanceLinkOverlayCmd cmd) {
        callJsEncodedArgumentFunction("showDecisionInstanceLinkOverlay", cmd);
    }

    /**
     * Shows or hides the overlays with documentation for all diagram elements that have it.
     *
     * @param cmd a command containing the documentation overlays visibility
     */
    public void showDocumentationOverlay(ShowDocumentationOverlayCmd cmd) {
        callJsEncodedArgumentFunction("showDocumentationOverlay", cmd);
    }

    /**
     * Shows or hides the overlays for all Call activity elements on the diagram to navigate to the called process.
     *
     * @param cmd command data
     */
    public void showCalledProcessOverlays(ShowCalledProcessOverlaysCmd cmd) {
        CalledProcessOverlayData overlayData = new CalledProcessOverlayData();
        overlayData.setVisible(cmd.isVisible());
        overlayData.setTooltipMessage(messages.getMessage("bpmnViewer.overlays.showCalledProcess.tooltipMessage"));

        callJsEncodedArgumentFunction("showCalledProcessOverlays", overlayData);
    }

    /**
     * Shows or hides the overlays for Business Rule Task elements on the diagram to navigate to the decision definition.
     *
     * @param cmd command data
     */
    public void showDecisionLinkOverlays(ShowDecisionLinkOverlaysCmd cmd) {
        DecisionLinkOverlayData overlayData = new DecisionLinkOverlayData();
        overlayData.setVisible(cmd.isVisible());
        overlayData.setTooltipMessage(messages.getMessage("bpmnViewer.overlays.showDecision.tooltipMessage"));

        callJsEncodedArgumentFunction("showDecisionLinkOverlays", overlayData);
    }

    /**
     * Shows the overlay for the specified diagram element to navigate to the called process instances.
     *
     * @param cmd command data
     */
    public void showCalledInstanceOverlay(ShowCalledInstanceOverlayCmd cmd) {
        CalledInstancesOverlayData overlayData = new CalledInstancesOverlayData();
        overlayData.setElementId(cmd.getElementId());
        overlayData.setProcessInstanceIds(cmd.getProcessInstanceIds());
        overlayData.setTooltipMessage(messages.getMessage("bpmnViewer.overlays.showCalledInstances.tooltipMessage"));

        callJsEncodedArgumentFunction("showCalledInstanceOverlay", overlayData);
    }

    /**
     * Sets a mode for the viewer.
     *
     * @param mode new mode
     */
    public void setMode(ViewerMode mode) {
        this.mode = mode;
        getElement().callJsFunction("setMode", mode != null ? mode.name() : null);
    }

    /**
     * Shows the "send message" overlays for the events on the BPMN diagram depending on the specified data.
     *
     * @param cmd command data
     */
    public void showSendMessageOverlays(ShowSendMessageOverlaysCmd cmd) {
        SendMessageOverlaysData data = new SendMessageOverlaysData();
        data.setUseActiveEvents(cmd.getUseActiveEvents());
        data.setUseStartEvents(cmd.getUseStartEvents());
        data.setTooltipMessage(messages.getMessage("bpmnViewer.overlays.sendMessage.tooltipMessage"));
        callJsEncodedArgumentFunction("showSendMessageOverlays", data);
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

    /**
     * Registers a component listener for the {@link ElementClickEvent}.
     *
     * @param listener a component listener for the {@link ElementClickEvent}
     * @return listener registration
     */
    public Registration addElementClickListener(ComponentEventListener<ElementClickEvent> listener) {
        return addListener(ElementClickEvent.class, listener);
    }

    /**
     * Registers a component listener for the {@link CalledProcessOverlayClickEvent}.
     *
     * @param listener a component listener for the {@link CalledProcessOverlayClickEvent}
     * @return listener registration
     */
    public Registration addCalledProcessOverlayClickListener(ComponentEventListener<CalledProcessOverlayClickEvent> listener) {
        return addListener(CalledProcessOverlayClickEvent.class, listener);
    }

    /**
     * Registers a component listener for the {@link DecisionLinkOverlayClickEvent}.
     *
     * @param listener a component listener for the {@link DecisionLinkOverlayClickEvent}
     * @return listener registration
     */
    public Registration addDecisionLinkOverlayClickListener(ComponentEventListener<DecisionLinkOverlayClickEvent> listener) {
        return addListener(DecisionLinkOverlayClickEvent.class, listener);
    }

    /**
     * Registers a component listener for the {@link SendMessageOverlayClickEvent}.
     *
     * @param listener a component listener for the {@link SendMessageOverlayClickEvent}
     * @return listener registration
     */
    public Registration addSendMessagesOverlayClickListener(ComponentEventListener<SendMessageOverlayClickEvent> listener) {
        return addListener(SendMessageOverlayClickEvent.class, listener);
    }

    /**
     * Registers a component listener for the {@link CalledProcessInstanceOverlayClickEvent}.
     *
     * @param listener a component listener for the {@link CalledProcessInstanceOverlayClickEvent}
     * @return listener registration
     */
    public Registration addCalledProcessInstanceOverlayClickListener(ComponentEventListener<CalledProcessInstanceOverlayClickEvent> listener) {
        return addListener(CalledProcessInstanceOverlayClickEvent.class, listener);
    }

    /**
     * Loads a list of activities from the diagram shown in the viewer.
     *
     * @return a completable feature to get a list of activities
     */
    public CompletableFuture<List<ActivityData>> getActivities() {
        VaadinSession session = VaadinSession.getCurrent();

        CompletableFuture<List<ActivityData>> completableFuture = new DeadlockDetectingCompletableFuture<>(
                session);

        getElement().callJsFunction("getActivities")
                .then(jsonValue -> {
                    List<ActivityData> activityDataList = JacksonUtils.readValue(jsonValue, new TypeReference<>() {
                    });
                    completableFuture.complete(activityDataList != null ? activityDataList : List.of());
                }, errorValue -> {
                    PendingJavaScriptResult.JavaScriptException exception = new PendingJavaScriptResult.JavaScriptException(errorValue);
                    completableFuture.completeExceptionally(exception);
                });

        return completableFuture;
    }

    /**
     * Adds an overlay with the specified count of running instances and incidents for the specified diagram element.
     *
     * @param cmd the command containing data to show the overlay with statistics for the diagram element
     */
    public void setActivityStatistics(SetActivityStatisticsCmd cmd) {
        ActivityStatisticsOverlayData overlayData = new ActivityStatisticsOverlayData(cmd.getElementId(),
                formatNumber(cmd.getInstanceCount()),
                formatNumber(cmd.getIncidentCount()));
        overlayData.setIncidentCountTooltipMessage(messages.formatMessage("", "bpmnViewer.overlays.incidentCount.tooltipMessage", cmd.getIncidentCount()));
        overlayData.setInstanceCountTooltipMessage(messages.formatMessage("", "bpmnViewer.overlays.runningInstanceCount.tooltipMessage", cmd.getInstanceCount()));

        callJsEncodedArgumentFunction("setActivityStatistics", overlayData);
    }

    /**
     * Removes an overlay with the activity statistics for the specified diagram element.
     *
     * @param activityId the identifier of the activity to remove statistics for
     */
    public void removeActivityStatistics(String activityId) {
        callJsEncodedArgumentFunction("removeActivityStatistics", activityId);
    }


    /**
     * Makes all overlays with the activity statistics visible or not.
     *
     * @param visible whether to show or hide the activity statistics overlays
     */
    public void setActivityStatisticsVisible(boolean visible) {
        getElement().callJsFunction("setActivityStatisticsVisible", visible);
    }

    /**
     * Invokes the client-side function passing the provided argument encoded as a JSON string.
     *
     * @param cmdName a name of the client-side function to invoke
     * @param cmd     an argument to encode and pass to the function
     * @throws IllegalArgumentException if the argument cannot be encoded to JSON
     */
    protected void callJsEncodedArgumentFunction(String cmdName, Object cmd) {
        String encodedCmd;
        try {
            encodedCmd = objectMapper.writeValueAsString(cmd);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(e);
        }
        getElement().callJsFunction(cmdName, encodedCmd);
    }

    /**
     * Formats a number to show in the overlay using the following rules:
     * <ol>
     *     <li>If the number is less than 1000, returns "as-is". </li>
     *     <li>If the number is greater than 1000, e.g. 1234, returns "1.2k" as a formatted string.</li>
     *     <li>If the number is greater than 1 million, e.g. 1 235 000, returns "1.2M" as a formatted string.</li>
     * </ol>
     *
     * @param number a number for the formatting
     * @return a string with a formatted number
     */
    @Nullable
    protected String formatNumber(@Nullable Integer number) {
        if (number == null) {
            return null;
        }
        if (number >= 1_000_000) {
            return millionFormat.format(number / 1_000_000.0);
        } else if (number >= 1_000) {
            return thousandFormat.format(number / 1_000.0);
        } else {
            return String.valueOf(number);
        }
    }
}
