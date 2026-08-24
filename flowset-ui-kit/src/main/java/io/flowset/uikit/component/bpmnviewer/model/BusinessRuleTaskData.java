/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

package io.flowset.uikit.component.bpmnviewer.model;

import io.jmix.core.metamodel.annotation.JmixEntity;

/**
 * Contains the values of the Business Rule Task element attributes.
 */
@JmixEntity
public class BusinessRuleTaskData {
    protected String elementId;
    protected String elementName;
    protected String decisionRef;
    protected String binding;
    protected String versionTag;
    protected String version;

    public BusinessRuleTaskData() {
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
     * Sets a referenced decision, e.g. decision definition key.
     *
     * @param decisionRef referenced decision
     */
    public void setDecisionRef(String decisionRef) {
        this.decisionRef = decisionRef;
    }

    /**
     * Sets business rule task binding.
     *
     * @param binding business rule task binding
     */
    public void setBinding(String binding) {
        this.binding = binding;
    }

    /**
     * Sets a version tag if the decision is bind with version tag.
     *
     * @param versionTag version tag for decision binding
     */
    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }

    /**
     * Sets a version if the decision is bind with version.
     *
     * @param version version for decision binding
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets a referenced decision.
     *
     * @return referenced decision
     */
    public String getDecisionRef() {
        return decisionRef;
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
     * @return business rule task binding type
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
