package io.openbpm.uikit.fragment.dmnviewer;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.function.SerializableConsumer;
import elemental.json.JsonValue;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.ViewComponent;
import io.openbpm.uikit.component.dmnviewer.DmnViewer;
import io.openbpm.uikit.component.dmnviewer.command.ShowDecisionInstanceCmd;
import io.openbpm.uikit.component.dmnviewer.event.DmnXmlImportCompleteEvent;

@FragmentDescriptor("dmn-viewer-fragment.xml")
public class DmnViewerFragment extends Fragment<Div> {

    @ViewComponent
    protected Div viewerContainer;

    protected DmnViewer dmnViewer;

    public void initViewer() {
        this.dmnViewer = uiComponents.create(DmnViewer.class);
        viewerContainer.removeAll();
        viewerContainer.add(dmnViewer);
    }

    public void setDmnXml(String dmnXml) {
        if (dmnViewer != null) {
            dmnViewer.setDmnXml(dmnXml);
        }
    }

    public void setDmnXml(String dmnXml, SerializableConsumer<JsonValue> callback) {
        if (dmnViewer != null) {
            dmnViewer.setDmnXml(dmnXml, callback);
        }
    }

    public void showDecisionDefinition(String decisionDefinitionKey, SerializableConsumer<JsonValue> callback) {
        if (dmnViewer != null) {
            dmnViewer.showDecisionDefinition(decisionDefinitionKey, callback);
        }
    }

    public void showDecisionInstance(ShowDecisionInstanceCmd cmd) {
        if (dmnViewer != null) {
            dmnViewer.showDecisionInstance(cmd);
        }
    }

    public void addImportCompleteListener(ComponentEventListener<DmnXmlImportCompleteEvent> listener) {
        if (dmnViewer != null) {
            dmnViewer.addImportCompleteListener(listener);
        }
    }
}
