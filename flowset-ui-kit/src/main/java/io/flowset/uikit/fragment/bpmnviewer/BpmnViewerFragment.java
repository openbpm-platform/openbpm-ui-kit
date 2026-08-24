/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.fragment.bpmnviewer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.flowset.uikit.component.bpmnviewer.BpmnViewer;
import io.flowset.uikit.component.bpmnviewer.ViewerMode;
import io.flowset.uikit.component.bpmnviewer.command.*;
import io.flowset.uikit.component.bpmnviewer.event.*;
import io.flowset.uikit.component.bpmnviewer.model.ActivityData;
import io.jmix.core.Messages;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.View;
import io.jmix.flowui.view.ViewComponent;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A wrapper fragment for displaying {@link BpmnViewer} with additional actions.
 */
@FragmentDescriptor("bpmn-viewer-fragment.xml")
@CssImport("./styles/bpmn-viewer-fragment.css")
public class BpmnViewerFragment extends Fragment<Div> {
    protected static final String ACTION_ACTIVE_CLASS_NAME = "bpmn-viewer-action-active";

    protected final static String BORDER_STYLES = String.join(" ", LumoUtility.Border.ALL, LumoUtility.BorderRadius.LARGE,
            LumoUtility.BorderColor.CONTRAST_30);

    @Autowired
    protected Messages messages;

    @ViewComponent
    protected Div viewerVBox;
    @ViewComponent
    protected Div viewerContainer;
    @ViewComponent
    protected JmixButton showDocumentationBtn;
    @ViewComponent
    protected JmixButton showStatisticsBtn;

    protected boolean noBorders;
    protected boolean showDocumentation;
    protected ViewerMode mode;
    protected BpmnViewer bpmnViewer;

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostInit(final View.InitEvent event) {
        onInit();
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostBeforeShow(final View.BeforeShowEvent event) {
        if (!noBorders) {
            viewerVBox.addClassNames(BORDER_STYLES);
        }
    }

    /**
     * Sets the visibility of the documentation button.
     *
     * @param visible whether the documentation button should be visible
     */
    public void showDocumentationButton(boolean visible) {
        showDocumentationBtn.setVisible(visible);

        showDocumentation = false;
        showDocumentationOverlay(false);
    }

    /**
     * Sets the visibility of the activity statistics button.
     *
     * @param visible whether the activity statistics button should be visible
     */
    public void showStatisticsButton(boolean visible) {
        showStatisticsBtn.setVisible(visible);

        if (visible) {
            updateButtonActiveState(showStatisticsBtn, true);
            showStatisticsBtn.setTitle(messages.getMessage("bpmnViewer.actions.hideActivityStatistics"));
        }
    }

    /**
     * Sets whether the viewer should have no borders.
     *
     * @param noBorders whether the viewer should have no borders
     */
    public void setNoBorders(boolean noBorders) {
        this.noBorders = noBorders;
    }

    /**
     * Sets the viewer mode.
     *
     * @param mode new viewer mode
     */
    public void setMode(ViewerMode mode) {
        this.mode = mode;
        if (bpmnViewer != null) {
            bpmnViewer.setMode(mode);
        }
    }

    /**
     * Gets the viewer mode.
     *
     * @return the current viewer mode
     */
    public ViewerMode getMode() {
        return mode;
    }

    /**
     * Initializes the BPMN viewer with the specified XML.
     *
     * @param bpmnXml the BPMN XML to initialize the viewer with
     */
    public void initViewer(String bpmnXml) {
        this.bpmnViewer = createBpmnViewer();
        this.bpmnViewer.setBpmnXml(bpmnXml);
        this.bpmnViewer.setMode(mode);

        viewerContainer.removeAll();
        viewerContainer.add(bpmnViewer);
    }

    /**
     * Adds a marker to the BPMN viewer.
     *
     * @param cmd the command to add the marker
     */
    public void addMarker(AddMarkerCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.addMarker(cmd);
        }
    }

    /**
     * Removes a marker from the BPMN viewer.
     *
     * @param cmd the command to remove the marker
     */
    public void removeMarker(RemoveMarkerCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.removeMarker(cmd);
        }
    }

    /**
     * Sets the color of the specified process element.
     *
     * @param cmd the command to set the element color
     */
    public void setElementColor(SetElementColorCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.setElementColor(cmd);
        }
    }

    /**
     * Sets the number of incidents for the specified process element.
     *
     * @param cmd the command to set the incident count
     */
    public void setIncidentCount(SetIncidentCountCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.setIncidentCount(cmd);
        }
    }

    /**
     * Sets the activity statistics for the specified process element.
     *
     * @param cmd the command to set the activity statistics
     */
    public void setActivityStatistics(SetActivityStatisticsCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.setActivityStatistics(cmd);
        }
    }

    /**
     * Removes the activity statistics for the specified process element.
     *
     * @param activityId the ID of the activity to remove statistics for
     */
    public void removeActivityStatistics(String activityId) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.removeActivityStatistics(activityId);
        }
    }

    /**
     * Sets the clickable elements in the viewer.
     *
     * @param activeElements the list of clickable element IDs
     */
    public void setActiveElements(Collection<String> activeElements) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.setActiveElements(activeElements);
        }
    }

    /**
     * Shows the decision instance link overlay for the specified command.
     *
     * @param cmd the command to show the overlay
     */
    public void showDecisionInstanceLinkOverlay(ShowDecisionInstanceLinkOverlayCmd cmd) {
        if (bpmnViewer != null) {
            this.bpmnViewer.showDecisionInstanceLinkOverlay(cmd);
        }
    }

    /**
     * Shows the documentation overlay for the specified command.
     *
     * @param cmd the command to show the overlay
     */
    public void showDocumentationOverlay(ShowDocumentationOverlayCmd cmd) {
        if (bpmnViewer != null) {
            this.bpmnViewer.showDocumentationOverlay(cmd);
        }
    }

    /**
     * Shows the send message overlays for the specified command.
     *
     * @param cmd the command to show the overlays
     */
    public void showSendMessageOverlays(ShowSendMessageOverlaysCmd cmd) {
        if (bpmnViewer != null) {
            this.bpmnViewer.showSendMessageOverlays(cmd);
        }
    }

    /**
     * Adds a listener for XML import completion events.
     *
     * @param listener the listener to add
     */
    public void addImportCompleteListener(ComponentEventListener<XmlImportCompleteEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addImportCompleteListener(listener);
        }
    }

    /**
     * Adds a listener for decision instance link overlay click events.
     *
     * @param listener the listener to add
     */
    public void addDecisionInstanceLinkOverlayClickListener(
            ComponentEventListener<DecisionInstanceLinkOverlayClickedEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addDecisionInstanceLinkOverlayClickListener(listener);
        }
    }

    /**
     * Adds a listener for documentation overlay click events.
     *
     * @param listener the listener to add
     */
    public void addDocumentationOverlayClickListener(
            ComponentEventListener<DocumentationOverlayClickedEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addDocumentationOverlayClickListener(listener);
        }
    }

    /**
     * Adds a listener for element click events.
     *
     * @param listener the listener to add
     * @return a registration for the listener
     */
    @Nullable
    public Registration addElementClickListener(ComponentEventListener<ElementClickEvent> listener) {
        if (bpmnViewer != null) {
            return bpmnViewer.addElementClickListener(listener);
        }
        return null;
    }

    /**
     * Adds a listener for called process overlay click events.
     *
     * @param listener the listener to add
     */
    public void addCalledProcessOverlayClickListener(ComponentEventListener<CalledProcessOverlayClickEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addCalledProcessOverlayClickListener(listener);
        }
    }

    /**
     * Adds a listener for decision link overlay click events.
     *
     * @param listener the listener to add
     */
    public void addDecisionLinkOverlayClickListener(ComponentEventListener<DecisionLinkOverlayClickEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addDecisionLinkOverlayClickListener(listener);
        }
    }

    /**
     * Adds a listener for send message overlay click events.
     *
     * @param listener the listener to add
     */
    public void addSendMessageOverlayClickListener(ComponentEventListener<SendMessageOverlayClickEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addSendMessagesOverlayClickListener(listener);
        }
    }

    /**
     * Adds a listener for called process instance overlay click events.
     *
     * @param listener the listener to add
     */
    public void addCalledProcessInstanceOverlayClickListener(ComponentEventListener<CalledProcessInstanceOverlayClickEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addCalledProcessInstanceOverlayClickListener(listener);
        }
    }

    /**
     * Shows the called instance overlay for the specified command.
     *
     * @param cmd the command to show the overlay
     */
    public void showCalledInstance(ShowCalledInstanceOverlayCmd cmd) {
        if (bpmnViewer != null) {
            bpmnViewer.showCalledInstanceOverlay(cmd);
        }
    }

    /**
     * Retrieves the activity data for the BPMN viewer.
     *
     * @return a CompletableFuture containing the list of activity data, or null if the viewer is not initialized
     */
    @Nullable
    public CompletableFuture<List<ActivityData>> getActivities() {
        if (bpmnViewer != null) {
            return bpmnViewer.getActivities();
        } else {
            return null;
        }
    }

    @Subscribe(id = "zoomResetBtn", subject = "clickListener")
    public void onZoomResetBtnClick(final ClickEvent<JmixButton> event) {
        if (bpmnViewer != null) {
            bpmnViewer.resetZoom();
        }
    }

    @Subscribe(id = "zoomInBtn", subject = "clickListener")
    public void onZoomInBtnClick(final ClickEvent<JmixButton> event) {
        if (bpmnViewer != null) {
            bpmnViewer.zoomByStep(1);
        }
    }

    @Subscribe(id = "zoomOutBtn", subject = "clickListener")
    public void onZoomOutBtnClick(final ClickEvent<JmixButton> event) {
        if (bpmnViewer != null) {
            bpmnViewer.zoomByStep(-1);
        }
    }

    @Subscribe(id = "showDocumentationBtn", subject = "clickListener")
    public void onShowDocumentationBtnClick(final ClickEvent<JmixButton> event) {
        showDocumentation = !showDocumentation;
        showDocumentationOverlay(showDocumentation);
        updateButtonActiveState(showDocumentationBtn, showDocumentation);
    }

    @Subscribe(id = "showStatisticsBtn", subject = "clickListener")
    protected void onShowStatisticsBtnClick(final ClickEvent<JmixButton> event) {
        if (isActiveButton(showStatisticsBtn)) {
            updateButtonActiveState(showStatisticsBtn, false);
            showStatisticsBtn.setTitle(messages.getMessage("bpmnViewer.actions.showActivityStatistics"));
            bpmnViewer.setActivityStatisticsVisible(false);
        } else {
            updateButtonActiveState(showStatisticsBtn, true);
            showStatisticsBtn.setTitle(messages.getMessage("bpmnViewer.actions.hideActivityStatistics"));
            bpmnViewer.setActivityStatisticsVisible(true);
        }
    }

    /**
     * Shows or hides the documentation overlays for the diagram elements that have documentation.
     *
     * @param showDocumentationOverlay whether the documentation overlays should be shown
     */
    protected void showDocumentationOverlay(boolean showDocumentationOverlay) {
        if (bpmnViewer != null) {
            ShowDocumentationOverlayCmd cmd = new ShowDocumentationOverlayCmd();

            cmd.setShowDocumentationOverlay(showDocumentationOverlay);
            bpmnViewer.showDocumentationOverlay(cmd);
        }
    }

    /**
     * Enables showing the overlays for navigating to the called process definitions for all
     * Call activity elements of the diagram.
     * <p>
     * The overlays are shown when the BPMN XML import is complete, so the method can be invoked
     * right after {@link #initViewer(String)}.
     *
     * @see #addCalledProcessOverlayClickListener(ComponentEventListener)
     */
    public void showCalledProcessOverlays() {
        if (bpmnViewer != null) {
            bpmnViewer.addImportCompleteListener(event -> {
                ShowCalledProcessOverlaysCmd cmd = new ShowCalledProcessOverlaysCmd();
                cmd.setVisible(true);

                bpmnViewer.showCalledProcessOverlays(cmd);
            });
        }
    }

    /**
     * Enables showing the overlays for navigating to the referenced decision definitions for all
     * Business Rule Task elements of the diagram.
     * <p>
     * The overlays are shown when the BPMN XML import is complete, so the method can be invoked
     * right after {@link #initViewer(String)}.
     *
     * @see #addDecisionLinkOverlayClickListener(ComponentEventListener)
     */
    public void showDecisionLinkOverlays() {
        if (bpmnViewer != null) {
            bpmnViewer.addImportCompleteListener(event -> {
                ShowDecisionLinkOverlaysCmd cmd = new ShowDecisionLinkOverlaysCmd();
                cmd.setVisible(true);

                bpmnViewer.showDecisionLinkOverlays(cmd);
            });
        }
    }

    /**
     * Marks the action button as active or inactive by adding or removing the corresponding class name.
     *
     * @param button button to update
     * @param active whether the button should be marked as active
     */
    protected void updateButtonActiveState(JmixButton button, boolean active) {
        if (active) {
            button.addClassName(ACTION_ACTIVE_CLASS_NAME);
        } else {
            button.removeClassName(ACTION_ACTIVE_CLASS_NAME);
        }
    }

    /**
     * Checks whether the action button is marked as active.
     *
     * @param button button to check
     * @return {@code true} if the button is marked as active, {@code false} otherwise
     */
    protected boolean isActiveButton(JmixButton button) {
        return button.hasClassName(ACTION_ACTIVE_CLASS_NAME);
    }

    /**
     * Extension point for initializing the BPMN viewer fragment.
     */
    protected void onInit() {

    }

    /**
     * Creates a {@link BpmnViewer} component to be placed into the fragment.
     * Can be overridden to provide a custom viewer implementation.
     *
     * @return a new BPMN viewer component
     */
    protected BpmnViewer createBpmnViewer() {
        return uiComponents.create(BpmnViewer.class);
    }
}
