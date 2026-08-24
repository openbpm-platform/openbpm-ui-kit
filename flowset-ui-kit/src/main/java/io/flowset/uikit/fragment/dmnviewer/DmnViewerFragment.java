package io.flowset.uikit.fragment.dmnviewer;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.View;
import io.jmix.flowui.view.ViewComponent;
import io.flowset.uikit.component.dmnviewer.DmnViewer;
import io.flowset.uikit.component.dmnviewer.command.ShowDecisionInstanceCmd;
import io.flowset.uikit.component.dmnviewer.event.DmnXmlImportCompleteEvent;
import tools.jackson.databind.JsonNode;

/**
 * A wrapper fragment for displaying {@link DmnViewer}.
 */
@FragmentDescriptor("dmn-viewer-fragment.xml")
public class DmnViewerFragment extends Fragment<Div> {
    protected final static String BORDER_STYLES = String.join(" ", LumoUtility.Border.ALL, LumoUtility.BorderRadius.LARGE,
            LumoUtility.BorderColor.CONTRAST_30);
    @ViewComponent
    protected Div viewerContainer;
    @ViewComponent
    protected Div viewerVBox;

    protected DmnViewer dmnViewer;
    protected boolean noBorders;

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostBeforeShow(final View.BeforeShowEvent event) {
        if (!noBorders) {
            viewerVBox.addClassNames(BORDER_STYLES);
        }
    }

    /**
     * Creates the DMN viewer component and places it into the fragment.
     * Must be invoked before any other method of the fragment, otherwise the calls have no effect.
     */
    public void initViewer() {
        this.dmnViewer = uiComponents.create(DmnViewer.class);
        viewerContainer.removeAll();
        viewerContainer.add(dmnViewer);
    }

    /**
     * Imports DMN XML schema into the viewer.
     *
     * @param dmnXml DMN XML schema
     */
    public void setDmnXml(String dmnXml) {
        if (dmnViewer != null) {
            dmnViewer.setDmnXml(dmnXml);
        }
    }

    /**
     * Imports DMN XML schema into the viewer and opens the specified decision table.
     *
     * @param dmnXml                DMN XML schema
     * @param decisionDefinitionKey decision definition key
     */
    public void setDmnXml(String dmnXml, String decisionDefinitionKey) {
        if (dmnViewer != null) {
            dmnViewer.setDmnXml(dmnXml, decisionDefinitionKey);
        }
    }

    /**
     * Imports DMN XML schema into the viewer and invokes the specified callback after import.
     *
     * @param dmnXml   DMN XML schema
     * @param callback callback to be invoked when the schema is imported
     */
    public void setDmnXml(String dmnXml, SerializableConsumer<JsonNode> callback) {
        if (dmnViewer != null) {
            dmnViewer.setDmnXml(dmnXml, callback);
        }
    }

    /**
     * Sets whether the viewer should have no borders.
     *
     * @param noBorders whether the viewer should have no borders
     */
    public void setNoBorders(boolean noBorders) {
        this.noBorders = noBorders;
    }

    /**
     * Shows the specified decision definition in the viewer and invokes the specified callback
     * after the decision definition is shown.
     *
     * @param decisionDefinitionKey decision definition key
     * @param callback              callback to be invoked when the decision definition is shown
     */
    public void showDecisionDefinition(String decisionDefinitionKey, SerializableConsumer<JsonNode> callback) {
        if (dmnViewer != null) {
            dmnViewer.showDecisionDefinition(decisionDefinitionKey, callback);
        }
    }

    /**
     * Highlights decision table row(s) using the command data.
     *
     * @param cmd command data
     */
    public void showDecisionInstance(ShowDecisionInstanceCmd cmd) {
        if (dmnViewer != null) {
            dmnViewer.showDecisionInstance(cmd);
        }
    }

    /**
     * Adds a listener for import complete events.
     *
     * @param listener listener to be added
     */
    public void addImportCompleteListener(ComponentEventListener<DmnXmlImportCompleteEvent> listener) {
        if (dmnViewer != null) {
            dmnViewer.addImportCompleteListener(listener);
        }
    }
}
