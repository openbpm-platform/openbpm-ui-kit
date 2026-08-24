package io.flowset.uikit.component.dmnviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.shared.Registration;
import io.flowset.uikit.component.dmnviewer.command.ShowDecisionInstanceCmd;
import io.flowset.uikit.component.dmnviewer.event.DmnXmlImportCompleteEvent;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Integrated <code>dmn-js</code> viewer for viewing DMN decisions.
 * <p>
 * After the DMN XML schema is imported by {@link #setDmnXml(String)}, the viewer shows the decision
 * requirements diagram of the schema, and a particular decision table can be opened either right away
 * by {@link #setDmnXml(String, String)} or later by {@link #showDecisionDefinition(String, SerializableConsumer)}.
 * The results of a decision instance evaluation can be highlighted on the opened decision table
 * by {@link #showDecisionInstance(ShowDecisionInstanceCmd)}.
 */
@Tag("flowset-control-dmn-viewer")
@NpmPackage(value = "dmn-js", version = "17.10.1")
@CssImport("dmn-js/dist/assets/dmn-font/css/dmn-embedded.css")
@JsModule("./src/dmn-viewer/dmn-viewer.ts")
public class DmnViewer extends Component {

    private final JsonMapper objectMapper = new JsonMapper();

    /**
     * Imports DMN XML schema into the viewer.
     *
     * @param dmnXml DMN XML schema
     */
    public void setDmnXml(String dmnXml) {
        getElement().callJsFunction("reloadSchema", dmnXml);
    }

    /**
     * Imports DMN XML schema into the viewer and opens the specified decision table.
     *
     * @param dmnXml                DMN XML schema
     * @param decisionDefinitionKey decision definition key
     */
    public void setDmnXml(String dmnXml, String decisionDefinitionKey) {
        getElement().callJsFunction("reloadSchema", dmnXml, decisionDefinitionKey);
    }

    /**
     * Imports DMN XML schema into the viewer and invokes the specified callback after import.
     *
     * @param dmnXml   DMN XML schema
     * @param callback callback to be invoked when the schema is imported
     */
    public void setDmnXml(String dmnXml, SerializableConsumer<JsonNode> callback) {
        getElement().callJsFunction("reloadSchema", dmnXml).then(callback);
    }

    /**
     * Shows the specified decision definition in the viewer and invokes the specified callback after the decision definition is shown.
     *
     * @param decisionDefinitionKey decision definition key
     * @param callback              callback to be invoked when the decision definition is shown
     */
    public void showDecisionDefinition(String decisionDefinitionKey, SerializableConsumer<JsonNode> callback) {
        getElement().callJsFunction("showDecisionDefinition", decisionDefinitionKey).then(callback);
    }

    /**
     * Highlights decision table row(s) using the command data.
     *
     * @param cmd command data
     */
    public void showDecisionInstance(ShowDecisionInstanceCmd cmd) {
        callJsEncodedArgumentFunction("showDecisionInstance", cmd);
    }

    /**
     * Adds a listener for import complete events.
     *
     * @param listener listener to be added
     * @return registration object for the listener
     */
    public Registration addImportCompleteListener(ComponentEventListener<DmnXmlImportCompleteEvent> listener) {
        return addListener(DmnXmlImportCompleteEvent.class, listener);
    }

    private void callJsEncodedArgumentFunction(String cmdName, Object cmd) {
        String encodedCmd;
        try {
            encodedCmd = objectMapper.writeValueAsString(cmd);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(e);
        }
        getElement().callJsFunction(cmdName, encodedCmd);
    }


}
