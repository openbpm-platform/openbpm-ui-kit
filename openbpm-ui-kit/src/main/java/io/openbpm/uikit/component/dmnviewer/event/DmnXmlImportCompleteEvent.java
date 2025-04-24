package io.openbpm.uikit.component.dmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.openbpm.uikit.component.dmnviewer.DmnViewer;

@DomEvent("dmn-xml-import-completed")
public class DmnXmlImportCompleteEvent extends ComponentEvent<DmnViewer> {

    private final String decisionDefinitionsJson;
    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     *                   side, <code>false</code> otherwise
     */
    public DmnXmlImportCompleteEvent(DmnViewer source, boolean fromClient,
                                     @EventData("event.decisionDefinitionsJson") String decisionDefinitionsJson) {
        super(source, fromClient);
        this.decisionDefinitionsJson = decisionDefinitionsJson;
    }

    public String getDecisionDefinitionsJson() {
        return decisionDefinitionsJson;
    }
}
