package io.openbpm.uikit.view.documentation;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.mdeditorflowui.component.MdEditor;

@Route(value = "bpmn-element-documentation-view")
@ViewController(id = "BpmnElementDocumentationView")
@ViewDescriptor(path = "bpmn-element-documentation-view.xml")
@DialogMode(width = "52em")
public class BpmnElementDocumentationView extends StandardView {

    @ViewComponent
    private MdEditor documentationViewer;
    @ViewComponent
    private Span elementIdLabel;
    @ViewComponent
    private Span elementTypeLabel;

    private String elementId;
    private String elementType;
    private String elementDocumentation;

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public void setElementDocumentation(String elementDocumentation) {
        this.elementDocumentation = elementDocumentation;
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        documentationViewer.setValue(elementDocumentation);
        elementIdLabel.setText(elementId);
        elementTypeLabel.setText(elementType);
    }
}
