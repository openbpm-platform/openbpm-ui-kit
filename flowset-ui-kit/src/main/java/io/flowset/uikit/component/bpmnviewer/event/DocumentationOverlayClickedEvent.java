package io.flowset.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.flowset.uikit.component.bpmnviewer.BpmnViewer;

/**
 * An event that is fired when the overlay with the diagram element documentation is clicked.
 */
@DomEvent("documentation-overlay-clicked")
public class DocumentationOverlayClickedEvent extends ComponentEvent<BpmnViewer> {

    private final String elementId;
    private final String elementType;
    private final String elementDocumentation;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source               the source component
     * @param fromClient           <code>true</code> if the event originated from the client
     *                             side, <code>false</code> otherwise
     * @param elementId            element id
     * @param elementType          element type
     * @param elementDocumentation element documentation
     */
    public DocumentationOverlayClickedEvent(BpmnViewer source, boolean fromClient,
                                            @EventData("event.elementId") String elementId,
                                            @EventData("event.elementType") String elementType,
                                            @EventData("event.elementDocumentation") String elementDocumentation) {
        super(source, fromClient);
        this.elementId = elementId;
        this.elementType = elementType;
        this.elementDocumentation = elementDocumentation;
    }

    /**
     * Gets an identifier of the diagram element whose documentation overlay has been clicked.
     *
     * @return element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Gets a type of the diagram element whose documentation overlay has been clicked,
     * e.g. {@code bpmn:UserTask}.
     *
     * @return element type
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * Gets the documentation text defined for the diagram element.
     *
     * @return element documentation
     */
    public String getElementDocumentation() {
        return elementDocumentation;
    }
}

