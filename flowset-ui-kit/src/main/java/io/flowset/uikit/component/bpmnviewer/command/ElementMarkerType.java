/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.command;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.jspecify.annotations.Nullable;


/**
 * A type of the marker that can be added to a BPMN diagram element to highlight it.
 * <p>
 * The marker identifier is applied as a CSS class name to the element on the client side,
 * so the appearance of every marker can be customized by overriding the corresponding
 * CSS custom properties.
 *
 * @see AddMarkerCmd
 * @see RemoveMarkerCmd
 */
public enum ElementMarkerType implements EnumClass<String> {

    /**
     * Marks an activity that is currently running: the element background is filled with the
     * {@code --bpmn-running-activity-color} color.
     * <p>
     * The marker is also taken into account when the "send message" overlays are shown for the
     * active events of the diagram.
     */
    RUNNING_ACTIVITY("running-activity"),

    /**
     * Marks an activity selected as a source of the process instance modification.
     */
    MODIFICATION_SOURCE_ACTIVITY("modification-source-activity"),

    /**
     * Marks an activity selected as a target of the process instance modification.
     */
    MODIFICATION_TARGET_ACTIVITY("modification-target-activity"),

    /**
     * Marks an activity to highlight it with the theme primary color.
     */
    PRIMARY_COLOR_ACTIVITY("primary-color-activity"),;

    private final String id;

    ElementMarkerType(String id) {
        this.id = id;
    }

    /**
     * Gets a marker identifier that is used as a CSS class name on the client side.
     *
     * @return marker identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Gets a marker type by its identifier.
     *
     * @param id marker identifier
     * @return the marker type with the provided identifier or {@code null} if there is no such marker type
     */
    @Nullable
    public static ElementMarkerType fromId(String id) {
        for (ElementMarkerType at : ElementMarkerType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
