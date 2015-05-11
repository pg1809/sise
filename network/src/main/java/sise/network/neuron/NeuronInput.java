package sise.network.neuron;

/**
 *
 * @author Wojciech Szałapski
 */
public class NeuronInput {

    private AbstractNeuron inputNeuron;

    private double weight;

    private double previousWeight;

    public NeuronInput(AbstractNeuron inputNeuron, double weight) {
        this.inputNeuron = inputNeuron;
        this.weight = weight;

        this.previousWeight = weight;
    }

    public AbstractNeuron getInputNeuron() {
        return inputNeuron;
    }

    public void setInputNeuron(AbstractNeuron inputNeuron) {
        this.inputNeuron = inputNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.previousWeight = this.weight;

        this.weight = weight;
    }

    public double getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(double previousWeight) {
        this.previousWeight = previousWeight;
    }
}
