/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

import io.flowset.uikit.component.bpmnviewer.InteractiveModeOptions;
import org.apache.commons.collections4.CollectionUtils;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * A command for {@link io.flowset.uikit.component.bpmnviewer.BpmnViewer} to switch the viewer
 * into the interactive mode.
 */
public class SetInteractiveModeCmd {

    protected Collection<String> activeElements = List.of();
    protected Collection<String> disabledElements = List.of();
    protected String activeElementMarker;
    protected boolean notPassedAsDisabled;

    /**
     * Creates the command with the default options: all elements clickable,
     * default hover coloring.
     */
    public SetInteractiveModeCmd() {
    }

    /**
     * Creates the command from the provided options; {@code null} options mean the defaults.
     *
     * @param options interactive mode options
     */
    public SetInteractiveModeCmd(@Nullable InteractiveModeOptions options) {
        if (options != null) {
            this.activeElements = CollectionUtils.emptyIfNull(options.getActiveElements());
            this.disabledElements = CollectionUtils.emptyIfNull(options.getDisabledElements());
            ElementMarkerType elementMarker = options.getActiveElementMarker();
            this.activeElementMarker = elementMarker != null ? elementMarker.getId() : null;
            this.notPassedAsDisabled = options.isNotPassedAsDisabled();
        }
    }

    /**
     * Gets identifiers of the elements that can be clicked on the diagram.
     *
     * @return element identifiers; when empty, all elements are clickable
     */
    public Collection<String> getActiveElements() {
        return activeElements;
    }

    /**
     * Sets identifiers of the elements that can be clicked on the diagram.
     *
     * @param activeElements element identifiers; when empty, all elements are clickable
     */
    public void setActiveElements(Collection<String> activeElements) {
        this.activeElements = activeElements;
    }

    /**
     * Gets identifiers of the elements excluded from the clickable elements.
     *
     * @return element identifiers
     */
    public Collection<String> getDisabledElements() {
        return disabledElements;
    }

    /**
     * Sets identifiers of the elements excluded from the clickable elements.
     *
     * @param disabledElements element identifiers
     */
    public void setDisabledElements(Collection<String> disabledElements) {
        this.disabledElements = disabledElements;
    }

    /**
     * Gets the identifier of the marker whose coloring is previewed on hover.
     *
     * @return marker identifier or {@code null} for the default hover coloring
     * @see ElementMarkerType#getId()
     */
    public String getActiveElementMarker() {
        return activeElementMarker;
    }

    /**
     * Sets the identifier of the marker whose coloring is previewed on hover.
     *
     * @param activeElementMarker marker identifier or {@code null} for the default hover coloring
     * @see ElementMarkerType#getId()
     */
    public void setActiveElementMarker(String activeElementMarker) {
        this.activeElementMarker = activeElementMarker;
    }

    /**
     * Gets whether the "not passed elements as disabled" presentation stays active
     * during the interactive session.
     *
     * @return whether the presentation stays active
     */
    public boolean isNotPassedAsDisabled() {
        return notPassedAsDisabled;
    }

    /**
     * Sets whether the "not passed elements as disabled" presentation stays active
     * during the interactive session.
     *
     * @param notPassedAsDisabled whether the presentation stays active
     */
    public void setNotPassedAsDisabled(boolean notPassedAsDisabled) {
        this.notPassedAsDisabled = notPassedAsDisabled;
    }
}
