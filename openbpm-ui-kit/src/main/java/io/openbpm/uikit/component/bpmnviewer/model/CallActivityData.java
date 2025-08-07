/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.openbpm.uikit.component.bpmnviewer.model;

/**
 * Contains the values of the Call activity element attributes.
 */
public class CallActivityData {
    protected String calledElement;
    protected String binding;
    protected String versionTag;
    protected String version;

    public CallActivityData() {
    }

    public void setCalledElement(String calledElement) {
        this.calledElement = calledElement;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCalledElement() {
        return calledElement;
    }

    public String getBinding() {
        return binding;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public String getVersion() {
        return version;
    }
}
