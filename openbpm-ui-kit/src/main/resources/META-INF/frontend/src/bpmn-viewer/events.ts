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