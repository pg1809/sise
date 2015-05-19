/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.ui.plot;

import sise.network.input.InputRow;
import java.util.List;

/**
 *
 * @author PiotrGrzelak
 */
public class ResultsPlotData {

    private String xAxisLabel;

    private String yAxisLabel;

    private String plotName;

    private List<InputRow> inputs;

    private List<double[]> outputs;

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    public void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public String getyAxisLabel() {
        return yAxisLabel;
    }

    public void setyAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public List<InputRow> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputRow> inputs) {
        this.inputs = inputs;
    }

    public List<double[]> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<double[]> outputs) {
        this.outputs = outputs;
    }
}
