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
import com.vaadin.flow.theme.lumo.LumoUtility;
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
import io.openbpm.uikit.component.bpmnviewer.event.DecisionInstanceLinkOverlayClickedEvent;
import io.openbpm.uikit.component.bpmnviewer.event.XmlImportCompleteEvent;

@FragmentDescriptor("bpmn-viewer-fragment.xml")
@CssImport("./styles/bpmn-viewer-fragment.css")
public class BpmnViewerFragment extends Fragment<Div> {
    protected final static String BORDER_STYLES = String.join(" ", LumoUtility.Border.ALL, LumoUtility.BorderRadius.LARGE,
            LumoUtility.BorderColor.CONTRAST_30);

    @ViewComponent
    protected Div viewerVBox;
    @ViewComponent
    protected Div viewerContainer;

    protected BpmnViewer bpmnViewer;

    @ViewComponent
    protected JmixButton zoomResetBtn;

    protected boolean noBorders;

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

    public void setNoBorders(boolean noBorders) {
        this.noBorders = noBorders;
    }

    public void initViewer(String bpmnXml) {
        this.bpmnViewer = uiComponents.create(BpmnViewer.class);
        this.bpmnViewer.setBpmnXml(bpmnXml);

        viewerContainer.removeAll();
        viewerContainer.add(bpmnViewer);
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

    @Subscribe(id = "zoomResetBtn", subject = "clickListener")
    public void onZoomResetBtnClick(final ClickEvent<JmixButton> event) {
        if (bpmnViewer != null) {
            bpmnViewer.resetZoom();
        }
    }
}
