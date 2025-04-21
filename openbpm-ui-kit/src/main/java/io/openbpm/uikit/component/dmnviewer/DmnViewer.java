package io.openbpm.uikit.component.dmnviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.function.SerializableConsumer;
import elemental.json.JsonValue;

@Tag("openbpm-control-dmn-viewer")
@NpmPackage(value = "dmn-js", version = "17.2.0")
@CssImport("dmn-js/dist/assets/dmn-font/css/dmn-embedded.css")
@JsModule("./src/dmn-viewer/dmn-viewer.ts")
public class DmnViewer extends Component {

    public void setDmnXml(String dmnXml) {
        getElement().callJsFunction("reloadSchema", dmnXml);
    }

    public void setDmnXml(String dmnXml, SerializableConsumer<JsonValue> callback) {
        getElement().callJsFunction("reloadSchema", dmnXml).then(callback);
    }
}
