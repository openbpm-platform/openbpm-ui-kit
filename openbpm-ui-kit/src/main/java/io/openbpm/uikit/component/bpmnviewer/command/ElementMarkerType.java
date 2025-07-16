/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.command;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum ElementMarkerType implements EnumClass<String> {

    RUNNING_ACTIVITY("running-activity"),
    MODIFICATION_SOURCE_ACTIVITY("modification-source-activity"),
    MODIFICATION_TARGET_ACTIVITY("modification-target-activity");

    private final String id;

    ElementMarkerType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

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