/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.fragment.bpmnviewer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.View;
import io.jmix.flowui.view.ViewComponent;
import io.openbpm.uikit.component.bpmnviewer.BpmnViewer;
import io.openbpm.uikit.component.bpmnviewer.command.AddMarkerCmd;
import io.openbpm.uikit.component.bpmnviewer.command.SetElementColorCmd;
import io.openbpm.uikit.component.bpmnviewer.command.SetIncidentCountCmd;
import io.openbpm.uikit.component.bpmnviewer.command.ShowDecisionInstanceLinkOverlayCmd;
import io.openbpm.uikit.component.bpmnviewer.command.ShowDocumentationOverlayCmd;
import io.openbpm.uikit.component.bpmnviewer.event.DecisionInstanceLinkOverlayClickedEvent;
import io.openbpm.uikit.component.bpmnviewer.event.DocumentationOverlayClickedEvent;
import io.openbpm.uikit.component.bpmnviewer.event.XmlImportCompleteEvent;
import io.openbpm.uikit.view.documentation.BpmnElementDocumentationView;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("bpmn-viewer-fragment.xml")
@CssImport("./styles/bpmn-viewer-fragment.css")
public class BpmnViewerFragment extends Fragment<Div> {

    protected final static String BORDER_STYLES = String.join(" ", LumoUtility.Border.ALL, LumoUtility.BorderRadius.LARGE,
            LumoUtility.BorderColor.CONTRAST_30);

    @ViewComponent
    protected Div viewerVBox;
    @ViewComponent
    protected Div viewerContainer;
    @ViewComponent
    protected JmixButton zoomResetBtn;
    @ViewComponent
    private JmixButton showDocumentationBtn;

    protected BpmnViewer bpmnViewer;
    protected boolean noBorders;
    protected boolean showDocumentation;
    protected Registration defaultDocumentationOverlayClickListenerRegistration;
    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostBeforeShow(final View.BeforeShowEvent event) {
        if (!noBorders) {
            viewerVBox.addClassNames(BORDER_STYLES);
        }

        Dialog dialog = UiComponentUtils.findDialog(this);
        if (dialog != null) {
            zoomResetBtn.getStyle().setPosition(Style.Position.ABSOLUTE);
        }
    }

    public void showDocumentationButton(boolean visible) {
        showDocumentationBtn.setVisible(visible);

        showDocumentation = false;
        showDocumentationOverlay(false);
    }

    public void setNoBorders(boolean noBorders) {
        this.noBorders = noBorders;
    }

    public void initViewer(String bpmnXml) {
        this.bpmnViewer = uiComponents.create(BpmnViewer.class);
        this.bpmnViewer.setBpmnXml(bpmnXml);

        viewerContainer.removeAll();
        viewerContainer.add(bpmnViewer);

        if (defaultDocumentationOverlayClickListenerRegistration != null) {
            defaultDocumentationOverlayClickListenerRegistration.remove();
        }

        defaultDocumentationOverlayClickListenerRegistration =
                this.bpmnViewer.addDocumentationOverlayClickListener(this::documentationOverlayClicked);
    }

    public void addMarker(AddMarkerCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.addMarker(cmd);
        }
    }

    public void setElementColor(SetElementColorCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.setElementColor(cmd);
        }
    }

    public void setIncidentCount(SetIncidentCountCmd cmd) {
        if (this.bpmnViewer != null) {
            this.bpmnViewer.setIncidentCount(cmd);
        }
    }

    public void showDecisionInstanceLinkOverlay(ShowDecisionInstanceLinkOverlayCmd cmd) {
        if (bpmnViewer != null) {
            this.bpmnViewer.showDecisionInstanceLinkOverlay(cmd);
        }
    }

    public void showDocumentationOverlay(ShowDocumentationOverlayCmd cmd) {
        if (bpmnViewer != null) {
            this.bpmnViewer.showDocumentationOverlay(cmd);
        }
    }

    public void addImportCompleteListener(ComponentEventListener<XmlImportCompleteEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addImportCompleteListener(listener);
        }
    }

    public void addDecisionInstanceLinkOverlayClickListener(
            ComponentEventListener<DecisionInstanceLinkOverlayClickedEvent> listener) {
        if (bpmnViewer != null) {
            bpmnViewer.addDecisionInstanceLinkOverlayClickListener(listener);
        }
    }

    public void addDocumentationOverlayClickListener(
            ComponentEventListener<DocumentationOverlayClickedEvent> listener) {
        if (bpmnViewer != null) {
            if (defaultDocumentationOverlayClickListenerRegistration != null) {
                defaultDocumentationOverlayClickListenerRegistration.remove();
                defaultDocumentationOverlayClickListenerRegistration = null;
            }
            bpmnViewer.addDocumentationOverlayClickListener(listener);
        }
    }

    @Subscribe(id = "zoomResetBtn", subject = "clickListener")
    public void onZoomResetBtnClick(final ClickEvent<JmixButton> event) {
        if (bpmnViewer != null) {
            bpmnViewer.resetZoom();
        }
    }

    @Subscribe(id = "showDocumentationBtn", subject = "clickListener")
    public void onShowDocumentationBtnClick(final ClickEvent<JmixButton> event) {
        showDocumentation = !showDocumentation;
        showDocumentationOverlay(showDocumentation);
    }

    protected void showDocumentationOverlay(boolean showDocumentationOverlay) {
        if (bpmnViewer != null) {
            ShowDocumentationOverlayCmd cmd = new ShowDocumentationOverlayCmd();

            cmd.setShowDocumentationOverlay(showDocumentationOverlay);
            bpmnViewer.showDocumentationOverlay(cmd);
        }
    }

    protected void documentationOverlayClicked(DocumentationOverlayClickedEvent event) {
        dialogWindows.view(UiComponentUtils.getCurrentView(), BpmnElementDocumentationView.class)
                .withViewConfigurer(documentationView -> {
            documentationView.setElementId(event.getElementId());
            documentationView.setElementType(event.getElementType());
            documentationView.setElementDocumentation(event.getElementDocumentation());
        }).open();
    }
}
