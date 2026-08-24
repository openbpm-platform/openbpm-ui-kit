package io.flowset.uikit.component.dmnviewer.command;

import io.flowset.uikit.component.dmnviewer.model.DecisionInstanceOutputData;

import java.util.List;

/**
 * A command for {@link io.flowset.uikit.component.dmnviewer.DmnViewer} to show the results of a decision instance
 * evaluation on the decision table.
 * <p>
 * For every provided output data the viewer highlights all the cells of the matched rule and appends the output
 * value to the cell defined by the row and column identifiers of the output data.
 */
public class ShowDecisionInstanceCmd {

    List<DecisionInstanceOutputData> outputDataList;

    /**
     * Gets a list of the decision instance output values to show on the decision table.
     *
     * @return a list of the decision instance output data
     */
    public List<DecisionInstanceOutputData> getOutputDataList() {
        return outputDataList;
    }

    /**
     * Sets a list of the decision instance output values to show on the decision table.
     *
     * @param outputDataList a list of the decision instance output data
     */
    public void setOutputDataList(List<DecisionInstanceOutputData> outputDataList) {
        this.outputDataList = outputDataList;
    }
}
