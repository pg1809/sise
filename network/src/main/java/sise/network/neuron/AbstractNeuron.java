package sise.network.neuron;

import sise.network.strategy.NeuronStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wojciech Szałapski
 */
public abstract class AbstractNeuron {

    protected double netValue;

    protected double output;

    protected double bias;

    protected double previousBias;

    protected double delta;

    protected List<AbstractNeuron> forwardNeurons = new ArrayList<>();

    protected List<NeuronInput> inputNeurons = new ArrayList<>();

    protected NeuronStrategy strategy;

    public void updateNetValue() {
        netValue = strategy.calculateNetValue(inputNeurons, bias);
    }

    public void updateOutput() {
        updateNetValue();
        output = strategy.transfer(netValue);
    }

    public void updateDelta(Double expectedOutput, double learningRate) {
        strategy.updateDelta(this, expectedOutput, learningRate);
    }

    public void updateParameters(double momentumFactor) {
        strategy.updateBias(this, delta, momentumFactor);
        strategy.updateWeights(this, delta, momentumFactor);
    }

    public void updateNeuron(double expectedOutput, double learningRate, double momentumFactor) {
        updateOutput();
        updateDelta(expectedOutput, learningRate);
        updateParameters(momentumFactor);
    }

    public void addForwardNeuron(AbstractNeuron neuron) {
        forwardNeurons.add(neuron);
    }

    public void addInputNeuron(NeuronInput inputNeuron) {
        inputNeurons.add(inputNeuron);
    }

    public boolean isInputNeuron() {
        return inputNeurons.isEmpty();
    }

    public boolean isOutputNeuron() {
        return forwardNeurons.isEmpty();
    }

    public double findForwardConnectionWeight(int forwardNeuronIndex) {
        AbstractNeuron forwardNeuron = forwardNeurons.get(forwardNeuronIndex);

        for (NeuronInput neuronInput : forwardNeuron.getInputNeurons()) {
            if (neuronInput.getInputNeuron() == this) {
                return neuronInput.getWeight();
            }
        }

        throw new RuntimeException("Neuron not found on the inputs list of given forward neuron");
    }

    public double getNetValue() {
        return netValue;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.previousBias = this.bias;

        this.bias = bias;
    }

    public double getPreviousBias() {
        return previousBias;
    }

    public void setPreviousBias(double previousBias) {
        this.previousBias = previousBias;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public List<AbstractNeuron> getForwardNeurons() {
        return forwardNeurons;
    }

    public void setForwardNeurons(List<AbstractNeuron> forwardNeurons) {
        this.forwardNeurons = forwardNeurons;
    }

    public List<NeuronInput> getInputNeurons() {
        return inputNeurons;
    }

    public void setInputNeurons(List<NeuronInput> inputNeurons) {
        this.inputNeurons = inputNeurons;
    }

    public NeuronStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(NeuronStrategy strategy) {
        this.strategy = strategy;
    }
}
