package io.openbpm.uikit.component.dmnviewer.model;

import io.openbpm.uikit.component.dmnviewer.DmnViewer;

/**
 * Contains the data required to show an output data of a decision instance for the {@link DmnViewer}.
 */
public class DecisionInstanceOutputData {

    private String dataRowId;
    private String dataColId;
    private String value;

    public String getDataRowId() {
        return dataRowId;
    }

    public void setDataRowId(String dataRowId) {
        this.dataRowId = dataRowId;
    }

    public String getDataColId() {
        return dataColId;
    }

    public void setDataColId(String dataColId) {
        this.dataColId = dataColId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
