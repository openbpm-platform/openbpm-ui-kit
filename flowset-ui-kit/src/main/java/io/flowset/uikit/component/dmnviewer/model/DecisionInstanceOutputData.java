package io.flowset.uikit.component.dmnviewer.model;

import io.flowset.uikit.component.dmnviewer.DmnViewer;

/**
 * Contains the data required to show an output data of a decision instance for the {@link DmnViewer}.
 */
public class DecisionInstanceOutputData {

    private String dataRowId;
    private String dataColId;
    private String value;

    /**
     * Gets an identifier of the decision table rule the output value belongs to.
     * All cells of the matched rule are highlighted in the viewer.
     *
     * @return a decision table row identifier
     */
    public String getDataRowId() {
        return dataRowId;
    }

    /**
     * Sets an identifier of the decision table rule the output value belongs to.
     *
     * @param dataRowId a decision table row identifier
     */
    public void setDataRowId(String dataRowId) {
        this.dataRowId = dataRowId;
    }

    /**
     * Gets an identifier of the decision table output column the value belongs to.
     * The value is shown in the cell defined by the row and column identifiers.
     *
     * @return a decision table column identifier
     */
    public String getDataColId() {
        return dataColId;
    }

    /**
     * Sets an identifier of the decision table output column the value belongs to.
     *
     * @param dataColId a decision table column identifier
     */
    public void setDataColId(String dataColId) {
        this.dataColId = dataColId;
    }

    /**
     * Gets a string representation of the decision instance output value.
     *
     * @return an output value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets a string representation of the decision instance output value.
     *
     * @param value an output value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
