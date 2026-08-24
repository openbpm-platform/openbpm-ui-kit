package io.flowset.uikit.component.dmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.flowset.uikit.component.dmnviewer.DmnViewer;

/**
 * An event that is fired when the DMN XML schema import into the viewer is complete.
 */
@DomEvent("dmn-xml-import-completed")
public class DmnXmlImportCompleteEvent extends ComponentEvent<DmnViewer> {

    private final String decisionDefinitionsJson;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source                  the source component
     * @param fromClient              <code>true</code> if the event originated from the client
     *                                side, <code>false</code> otherwise
     * @param decisionDefinitionsJson JSON containing information about list of imported decision definitions
     */
    public DmnXmlImportCompleteEvent(DmnViewer source, boolean fromClient,
                                     @EventData("event.decisionDefinitionsJson") String decisionDefinitionsJson) {
        super(source, fromClient);
        this.decisionDefinitionsJson = decisionDefinitionsJson;
    }

    /**
     * Gets information about the decision definitions found in the imported DMN XML schema.
     * The JSON contains an array of objects with the {@code key} and {@code name} attributes
     * for every decision of the schema.
     *
     * @return JSON containing information about the list of imported decision definitions
     */
    public String getDecisionDefinitionsJson() {
        return decisionDefinitionsJson;
    }
}
