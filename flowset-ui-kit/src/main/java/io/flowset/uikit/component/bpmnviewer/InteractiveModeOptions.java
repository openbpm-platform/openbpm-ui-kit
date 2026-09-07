/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer;

import io.flowset.uikit.component.bpmnviewer.command.ElementMarkerType;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

/**
 * Options of the {@link ViewerMode#INTERACTIVE} viewer mode,
 * see {@link BpmnViewer#setInteractiveMode(InteractiveModeOptions)}.
 */
public class InteractiveModeOptions {

    protected Collection<String> activeElements;
    protected Collection<String> disabledElements;
    protected ElementMarkerType activeElementMarker;
    protected boolean notPassedAsDisabled;

    /**
     * Element identifiers that can be clicked on the diagram; when empty, all elements
     * are clickable.
     */
    @Nullable
    public Collection<String> getActiveElements() {
        return activeElements;
    }

    public void setActiveElements(@Nullable Collection<String> activeElements) {
        this.activeElements = activeElements;
    }

    /**
     * Fluent variant of {@link #setActiveElements(Collection)}.
     *
     * @return this instance for chaining
     */
    public InteractiveModeOptions withActiveElements(@Nullable Collection<String> activeElements) {
        setActiveElements(activeElements);
        return this;
    }

    /**
     * Element identifiers excluded from the clickable elements.
     */
    @Nullable
    public Collection<String> getDisabledElements() {
        return disabledElements;
    }

    public void setDisabledElements(@Nullable Collection<String> disabledElements) {
        this.disabledElements = disabledElements;
    }

    /**
     * Fluent variant of {@link #setDisabledElements(Collection)}.
     *
     * @return this instance for chaining
     */
    public InteractiveModeOptions withDisabledElements(@Nullable Collection<String> disabledElements) {
        setDisabledElements(disabledElements);
        return this;
    }

    /**
     * Marker whose coloring is previewed on hover over the clickable elements.
     * When not set, the default hover coloring is used.
     */
    @Nullable
    public ElementMarkerType getActiveElementMarker() {
        return activeElementMarker;
    }

    public void setActiveElementMarker(@Nullable ElementMarkerType activeElementMarker) {
        this.activeElementMarker = activeElementMarker;
    }

    /**
     * Fluent variant of {@link #setActiveElementMarker(ElementMarkerType)}.
     *
     * @return this instance for chaining
     */
    public InteractiveModeOptions withActiveElementMarker(@Nullable ElementMarkerType activeElementMarker) {
        setActiveElementMarker(activeElementMarker);
        return this;
    }

    /**
     * Whether the "not passed elements as disabled" presentation enabled by
     * {@link BpmnViewer#setShowNotPassedAsDisabled(boolean)} stays active during
     * this interactive session. By default, it is suspended in the interactive mode.
     */
    public boolean isNotPassedAsDisabled() {
        return notPassedAsDisabled;
    }

    public void setNotPassedAsDisabled(boolean notPassedAsDisabled) {
        this.notPassedAsDisabled = notPassedAsDisabled;
    }

    /**
     * Fluent variant of {@link #setNotPassedAsDisabled(boolean)}.
     *
     * @return this instance for chaining
     */
    public InteractiveModeOptions withNotPassedAsDisabled(boolean notPassedAsDisabled) {
        setNotPassedAsDisabled(notPassedAsDisabled);
        return this;
    }
}
