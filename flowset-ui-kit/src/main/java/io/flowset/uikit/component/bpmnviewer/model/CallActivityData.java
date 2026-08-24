/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

import io.jmix.core.metamodel.annotation.JmixEntity;

/**
 * Contains the values of the Call activity element attributes.
 */
@JmixEntity
public class CallActivityData {
    protected String elementId;
    protected String elementName;
    protected String calledElement;
    protected String binding;
    protected String versionTag;
    protected String version;

    public CallActivityData() {
    }

    /**
     * Sets a BPMN diagram element identifier.
     *
     * @param elementId a BPMN diagram element identifier
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Sets a BPMN diagram element name.
     *
     * @param elementName a BPMN diagram element name
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * Sets a called element, e.g. process definition key.
     *
     * @param calledElement called element
     */
    public void setCalledElement(String calledElement) {
        this.calledElement = calledElement;
    }

    /**
     * Sets call activity binding.
     *
     * @param binding call activity binding.
     */
    public void setBinding(String binding) {
        this.binding = binding;
    }

    /**
     * Sets a version tag if call activity is bind with version tag.
     *
     * @param versionTag version tag for call activity binding
     */
    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }

    /**
     * Sets a version if call activity is bind with version.
     *
     * @param version version for call activity binding
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets a called element.
     *
     * @return called element
     */
    public String getCalledElement() {
        return calledElement;
    }

    /**
     * Gets an element identifier.
     *
     * @return element identifier
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Gets an element name.
     *
     * @return element name
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Gets a binding.
     *
     * @return call activity binding type
     */
    public String getBinding() {
        return binding;
    }

    /**
     * Gets a version tag for binding.
     *
     * @return version tag
     */
    public String getVersionTag() {
        return versionTag;
    }

    /**
     * Gets a version for binding.
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }
}
