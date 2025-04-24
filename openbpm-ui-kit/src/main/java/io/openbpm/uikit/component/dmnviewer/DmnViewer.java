package io.openbpm.uikit.component.dmnviewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.shared.Registration;
import elemental.json.JsonValue;
import io.openbpm.uikit.component.dmnviewer.command.ShowDecisionInstanceCmd;
import io.openbpm.uikit.component.dmnviewer.event.DmnXmlImportCompleteEvent;

@Tag("openbpm-control-dmn-viewer")
@NpmPackage(value = "dmn-js", version = "17.2.0")
@CssImport("dmn-js/dist/assets/dmn-font/css/dmn-embedded.css")
@JsModule("./src/dmn-viewer/dmn-viewer.ts")
public class DmnViewer extends Component {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setDmnXml(String dmnXml) {
        getElement().callJsFunction("reloadSchema", dmnXml);
    }

    public void setDmnXml(String dmnXml, SerializableConsumer<JsonValue> callback) {
        getElement().callJsFunction("reloadSchema", dmnXml).then(callback);
    }

    public void showDecisionDefinition(String decisionDefinitionKey, SerializableConsumer<JsonValue> callback) {
        getElement().callJsFunction("showDecisionDefinition", decisionDefinitionKey).then(callback);
    }

    public void showDecisionInstance(ShowDecisionInstanceCmd cmd) {
        callJsEncodedArgumentFunction("showDecisionInstance", cmd);
    }

    public Registration addImportCompleteListener(ComponentEventListener<DmnXmlImportCompleteEvent> listener) {
        return addListener(DmnXmlImportCompleteEvent.class, listener);
    }

    private void callJsEncodedArgumentFunction(String cmdName, Object cmd) {
        String encodedCmd;
        try {
            encodedCmd = objectMapper.writeValueAsString(cmd);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        getElement().callJsFunction(cmdName, encodedCmd);
    }
}
