package io.openbpm.uikit.component.bpmnviewer.command;

public class ShowDecisionInstanceLinkOverlayCmd {

    protected String activityId;
    protected String decisionInstanceId;
    protected String tooltipMessage;

    public ShowDecisionInstanceLinkOverlayCmd(String activityId, String decisionInstanceId, String tooltipMessage) {
        this.activityId = activityId;
        this.decisionInstanceId = decisionInstanceId;
        this.tooltipMessage = tooltipMessage;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getDecisionInstanceId() {
        return decisionInstanceId;
    }

    public void setDecisionInstanceId(String decisionInstanceId) {
        this.decisionInstanceId = decisionInstanceId;
    }

    public String getTooltipMessage() {
        return tooltipMessage;
    }

    public void setTooltipMessage(String tooltipMessage) {
        this.tooltipMessage = tooltipMessage;
    }
}
