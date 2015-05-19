package sise.network;

import sise.network.layer.NeuronLayer;
import sise.network.neuron.AbstractNeuron;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wojciech Szałapski
 */
public abstract class AbstractNetwork {

    protected NeuronLayer inputLayer = new NeuronLayer();

    protected List<NeuronLayer> hiddenLayers = new ArrayList<>();

    protected NeuronLayer outputLayer = new NeuronLayer();

    public void readSample(double[] sample) {
        List<AbstractNeuron> inputNeurons = inputLayer.getNeurons();
        for (int i = 0; i < inputNeurons.size(); ++i) {
            inputNeurons.get(i).setOutput(sample[i]);
        }
    }

    public double[] runNetwork(double[] sample) {
        readSample(sample);

        for (NeuronLayer hiddenLayer : hiddenLayers) {
            hiddenLayer.updateOutput();
        }

        return outputLayer.updateAndGetOutput();
    }

    public void addHiddenLayer(NeuronLayer layer) {
        hiddenLayers.add(layer);
    }

    public NeuronLayer getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(NeuronLayer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public List<NeuronLayer> getHiddenLayers() {
        return hiddenLayers;
    }

    public void setHiddenLayers(List<NeuronLayer> hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
    }

    public NeuronLayer getOutputLayer() {
        return outputLayer;
    }

    public void setOutputLayer(NeuronLayer outputLayer) {
        this.outputLayer = outputLayer;
    }
}
