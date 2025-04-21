package io.openbpm.uikit.fragment.dmnviewer;

import com.vaadin.flow.component.html.Div;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.ViewComponent;
import io.openbpm.uikit.component.dmnviewer.DmnViewer;

@FragmentDescriptor("dmn-viewer-fragment.xml")
public class DmnViewerFragment extends Fragment<Div> {

    @ViewComponent
    protected Div viewerContainer;

    protected DmnViewer dmnViewer;

    public void initViewer(String bpmnXml) {
        this.dmnViewer = uiComponents.create(DmnViewer.class);
        this.dmnViewer.setDmnXml(bpmnXml);
        viewerContainer.removeAll();
        viewerContainer.add(dmnViewer);
    }
}
