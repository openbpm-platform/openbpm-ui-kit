package io.openbpm.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.openbpm.uikit.component.bpmnviewer.BpmnViewer;

@DomEvent("documentation-overlay-clicked")
public class DocumentationOverlayClickedEvent extends ComponentEvent<BpmnViewer> {

    private final String elementId;
    private final String elementType;
    private final String elementDocumentation;
    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     *                   side, <code>false</code> otherwise
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

    public String getElementId() {
        return elementId;
    }

    public String getElementType() {
        return elementType;
    }

    public String getElementDocumentation() {
        return elementDocumentation;
    }
}

