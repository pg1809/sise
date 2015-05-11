/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.ui.plot;

import sise.network.input.InputRow;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author PiotrGrzelak
 */
public class PlotGenerator {

    private int chartWidth;

    private int chartHeight;

    private int errorChartId = 1;

    private int dataChartId = 1;

    public PlotGenerator(int chartWidth, int chartHeight) {
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
    }

    public PlotGenerator() {
        this(1024, 768);
    }

    public void generateErrorChart(List<Double> errors, String plotFileName) throws IOException {
        XYSeries data = new XYSeries("Errors");

        for (int i = 1; i <= errors.size(); ++i) {
            data.add(i, errors.get(i - 1));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(data);
        JFreeChart chart = ChartFactory.createXYLineChart("Squared error", "Epoch number", "Squared Error", dataset, PlotOrientation.VERTICAL, false, true, true);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        chart.getXYPlot().setRenderer(renderer);

        File XYChart = new File(plotFileName);
        ChartUtilities.saveChartAsJPEG(XYChart, chart, chartWidth, chartHeight);
    }

    public void generateErrorChart(List<Double> errors) throws IOException {
        generateErrorChart(errors, "errorChart" + (errorChartId++) + ".png");
    }

    public void generateResultsChart(ResultsPlotData data, String fileName) throws IOException {
        XYSeries expectedData = new XYSeries("Expected output");
        XYSeries networkData = new XYSeries("Network output");

        List<double[]> inputs = new ArrayList(data.getInputs().size());
        data.getInputs().stream().forEach(
                (InputRow row) -> {inputs.add(row.getValues());}
            );
        
        List<double[]> expectedOutputs = new ArrayList(data.getInputs().size());
        data.getInputs().stream().forEach(
                (InputRow row) -> {expectedOutputs.add(row.getExpectedOutput());}
            );
        
        List<double[]> outputs = data.getOutputs();
        
        for (int i = 0; i < inputs.size(); ++i) {
            expectedData.add(inputs.get(i)[0], expectedOutputs.get(i)[0]);
            networkData.add(inputs.get(i)[0], outputs.get(i)[0]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(expectedData);
        dataset.addSeries(networkData);
        JFreeChart chart = ChartFactory.createXYLineChart(data.getPlotName(), data.getxAxisLabel(), data.getyAxisLabel(),
                dataset, PlotOrientation.VERTICAL, true, true, true);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesLinesVisible(1, false);
        chart.getXYPlot().setRenderer(renderer);

        File XYChart = new File(fileName);
        ChartUtilities.saveChartAsJPEG(XYChart, chart, chartWidth, chartHeight);
    }
    
    public void generateResultsChart(ResultsPlotData data) throws IOException {
        generateResultsChart(data, "results" + (dataChartId++) + ".png");
    }
}
