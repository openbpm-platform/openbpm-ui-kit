package io.flowset.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to show the overlay for navigating
 * to the evaluated decision instance from the diagram element.
 */
public class ShowDecisionInstanceLinkOverlayCmd {

    protected String activityId;
    protected String decisionInstanceId;
    protected String tooltipMessage;

    /**
     * Creates the command with all the required attributes.
     *
     * @param activityId         a diagram element identifier to show the overlay for
     * @param decisionInstanceId an identifier of the decision instance to navigate to
     * @param tooltipMessage     a tooltip message to show for the overlay
     */
    public ShowDecisionInstanceLinkOverlayCmd(String activityId, String decisionInstanceId, String tooltipMessage) {
        this.activityId = activityId;
        this.decisionInstanceId = decisionInstanceId;
        this.tooltipMessage = tooltipMessage;
    }

    /**
     * Gets a diagram element identifier the overlay is shown for.
     *
     * @return a diagram element identifier
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * Sets a diagram element identifier to show the overlay for.
     *
     * @param activityId a diagram element identifier
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    /**
     * Gets an identifier of the decision instance to navigate to.
     *
     * @return decision instance identifier
     */
    public String getDecisionInstanceId() {
        return decisionInstanceId;
    }

    /**
     * Sets an identifier of the decision instance to navigate to.
     *
     * @param decisionInstanceId decision instance identifier
     */
    public void setDecisionInstanceId(String decisionInstanceId) {
        this.decisionInstanceId = decisionInstanceId;
    }

    /**
     * Gets a tooltip message shown for the overlay.
     *
     * @return a tooltip message
     */
    public String getTooltipMessage() {
        return tooltipMessage;
    }

    /**
     * Sets a tooltip message shown for the overlay.
     *
     * @param tooltipMessage a tooltip message
     */
    public void setTooltipMessage(String tooltipMessage) {
        this.tooltipMessage = tooltipMessage;
    }
}
