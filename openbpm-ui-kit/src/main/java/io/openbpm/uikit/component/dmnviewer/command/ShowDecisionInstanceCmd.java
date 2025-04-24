package io.openbpm.uikit.component.dmnviewer.command;

import io.openbpm.uikit.component.dmnviewer.model.DecisionInstanceOutputData;

import java.util.List;

public class ShowDecisionInstanceCmd {

    List<DecisionInstanceOutputData> outputDataList;

    public List<DecisionInstanceOutputData> getOutputDataList() {
        return outputDataList;
    }

    public void setOutputDataList(List<DecisionInstanceOutputData> outputDataList) {
        this.outputDataList = outputDataList;
    }
}
