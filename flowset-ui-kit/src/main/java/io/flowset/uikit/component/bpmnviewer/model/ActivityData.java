/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

/**
 * Contains data about the BPMN process activity shown on the BPMN diagram.
 */
public class ActivityData {
    private String id;
    private String name;
    private String type;

    public ActivityData() {
    }

    /**
     * Gets an activity identifier.
     *
     * @return activity identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets an activity identifier.
     *
     * @param id activity identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets an activity name.
     *
     * @return activity name or {@code null} if the activity has no name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets an activity name.
     *
     * @param name activity name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets an activity type, e.g. {@code bpmn:UserTask}.
     *
     * @return activity type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets an activity type, e.g. {@code bpmn:UserTask}.
     *
     * @param type activity type
     */
    public void setType(String type) {
        this.type = type;
    }
}
