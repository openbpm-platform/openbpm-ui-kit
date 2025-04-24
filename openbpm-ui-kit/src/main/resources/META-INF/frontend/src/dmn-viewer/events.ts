export class XmlImportCompleteEvent extends Event {
    public decisionDefinitionsJson: string;

    public constructor(decisionDefinitionsJson: string) {
        super("dmn-xml-import-completed");
        this.decisionDefinitionsJson = decisionDefinitionsJson;
    }
}
