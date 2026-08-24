package io.flowset.uikit.component.bpmnviewer.command;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to show or hide the overlays
 * with documentation for the diagram elements that have it.
 */
public class ShowDocumentationOverlayCmd {

    protected boolean showDocumentationOverlay;

    /**
     * Gets whether the documentation overlays should be shown.
     *
     * @return {@code true} if the documentation overlays should be shown, {@code false} otherwise
     */
    public boolean isShowDocumentationOverlay() {
        return showDocumentationOverlay;
    }

    /**
     * Sets whether the documentation overlays should be shown.
     *
     * @param showDocumentationOverlay {@code true} to show the documentation overlays, {@code false} to hide them
     */
    public void setShowDocumentationOverlay(boolean showDocumentationOverlay) {
        this.showDocumentationOverlay = showDocumentationOverlay;
    }
}
