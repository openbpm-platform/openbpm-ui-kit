package io.flowset.uikit.component.bpmnviewer.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import io.flowset.uikit.component.bpmnviewer.BpmnViewer;

/**
 * An event that is fired when the overlay for navigating to the evaluated decision instance is clicked.
 */
@DomEvent("decision-instance-link-overlay-clicked")
public class DecisionInstanceLinkOverlayClickedEvent extends ComponentEvent<BpmnViewer> {

    private final String decisionInstanceId;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source             the source component
     * @param fromClient         <code>true</code> if the event originated from the client
     *                           side, <code>false</code> otherwise
     * @param decisionInstanceId decision identifier for navigation
     */
    public DecisionInstanceLinkOverlayClickedEvent(BpmnViewer source, boolean fromClient,
                                                   @EventData("event.decisionInstanceId") String decisionInstanceId) {
        super(source, fromClient);
        this.decisionInstanceId = decisionInstanceId;
    }

    /**
     * Gets an identifier of the decision instance evaluated by the element whose overlay has been clicked.
     *
     * @return decision instance identifier for navigation
     */
    public String getDecisionInstanceId() {
        return decisionInstanceId;
    }
}

