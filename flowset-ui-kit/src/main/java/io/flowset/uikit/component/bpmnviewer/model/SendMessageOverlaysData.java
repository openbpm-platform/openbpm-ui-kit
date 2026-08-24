package io.flowset.uikit.component.bpmnviewer.model;

/**
 * Contains the data required to show the "send message" overlays for the process elements
 * in the {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer}.
 * <p>
 * The overlays are considered for the message events of the diagram only, and the flags of this class define
 * which of them get an overlay. If {@link #getUseActiveEvents()} is set, only the events of the currently running
 * activities are taken and {@link #getUseStartEvents()} is ignored, otherwise the start events are taken.
 */
public class SendMessageOverlaysData {
    protected String tooltipMessage;
    protected Boolean useActiveEvents;
    protected Boolean useStartEvents;

    /**
     * Gets a tooltip message shown for the overlays. The name of the message defined for the event
     * is appended to it, so the resulting tooltip looks like "&lt;tooltip message&gt;: &lt;message name&gt;".
     *
     * @return a tooltip message
     */
    public String getTooltipMessage() {
        return tooltipMessage;
    }

    /**
     * Sets a tooltip message shown for the overlays.
     *
     * @param tooltipMessage a tooltip message
     */
    public void setTooltipMessage(String tooltipMessage) {
        this.tooltipMessage = tooltipMessage;
    }

    /**
     * Gets whether the overlays are shown for the message events that are active at the moment, i.e. the events
     * marked with the {@link io.flowset.uikit.component.bpmnviewer.command.ElementMarkerType#RUNNING_ACTIVITY}
     * marker, and the boundary events attached to such activities.
     * <p>
     * If the flag is set, {@link #getUseStartEvents()} is not taken into account.
     *
     * @return whether the overlays are shown for the message events active at the moment
     */
    public Boolean getUseActiveEvents() {
        return useActiveEvents;
    }

    /**
     * Sets whether the overlays are shown for the message events that are active at the moment.
     *
     * @param useActiveEvents whether the overlays are shown for the message events active at the moment
     */
    public void setUseActiveEvents(Boolean useActiveEvents) {
        this.useActiveEvents = useActiveEvents;
    }

    /**
     * Gets whether the overlays are shown for the start message events of the diagram.
     * <p>
     * The flag is taken into account only if {@link #getUseActiveEvents()} is not set.
     *
     * @return whether the overlays are shown for the start message events
     */
    public Boolean getUseStartEvents() {
        return useStartEvents;
    }

    /**
     * Sets whether the overlays are shown for the start message events of the diagram.
     *
     * @param useStartEvents whether the overlays are shown for the start message events
     */
    public void setUseStartEvents(Boolean useStartEvents) {
        this.useStartEvents = useStartEvents;
    }
}
