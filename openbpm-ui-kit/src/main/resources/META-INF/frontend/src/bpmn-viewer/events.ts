/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

export class XmlImportCompleteEvent extends Event {
    public static readonly NAME = "xml-import-complete";

    public processDefinitionsJson: string;

    public constructor(processDefinitionsJson: string) {
        super(XmlImportCompleteEvent.NAME);
        this.processDefinitionsJson = processDefinitionsJson;
    }
}

export class DecisionInstanceLinkOverlayClickedEvent extends Event {
    public decisionInstanceId: string;

    public constructor(decisionInstanceId: string) {
        super("decision-instance-link-overlay-clicked");
        this.decisionInstanceId = decisionInstanceId;
    }
}

export class DocumentationOverlayClickedEvent extends Event {
    public elementId: string;
    public elementType: string;
    public elementDocumentation: string;

    public constructor(elementId: string, elementType: string, elementDocumentation: string) {
        super("documentation-overlay-clicked");

        this.elementId = elementId;
        this.elementType = elementType;
        this.elementDocumentation = elementDocumentation;
    }
}

export class BpmnElementClickEvent extends Event {
    public elementId: string;
    public elementType: string;
    public elementName: string | undefined;

    public constructor(elementId: string, elementType: string, elementName?: string) {
        super("bpmn-element-clicked");
        this.elementId = elementId;
        this.elementType = elementType;
        this.elementName = elementName;
    }
}

export class CalledProcessOverlayClickEvent extends Event {
    public elementId: string;
    public callActivity: JSON;

    public constructor(elementId: string, callActivity: JSON) {
        super("called-process-overlay-clicked");

        this.elementId = elementId;
        this.callActivity = callActivity;
    }

}

export class CalledProcessInstanceOverlayClickEvent extends Event {
    public details: JSON;

    public constructor(details: JSON) {
        super("called-process-instance-overlay-clicked");
        this.details = details;
    }
}