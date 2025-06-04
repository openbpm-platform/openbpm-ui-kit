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

    public constructor(elementId:string, elementType:string, elementDocumentation: string) {
        super("documentation-overlay-clicked");

        this.elementId = elementId;
        this.elementType = elementType;
        this.elementDocumentation = elementDocumentation;
    }
}