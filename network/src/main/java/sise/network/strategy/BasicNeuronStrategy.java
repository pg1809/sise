package sise.network.strategy;

import sise.network.neuron.AbstractNeuron;
import sise.network.neuron.NeuronInput;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Wojciech Szałapski
 */
public abstract class BasicNeuronStrategy implements NeuronStrategy {

    @Override
    public double calculateNetValue(Collection<NeuronInput> inputNeurons, double bias) {
        double netValue = bias;
        for (NeuronInput neuron : inputNeurons) {
            netValue += neuron.getInputNeuron().getOutput() * neuron.getWeight();
        }

        return netValue;
    }

    @Override
    public void updateBias(AbstractNeuron neuron, double delta, double momentumFactor) {
    }

    @Override
    public void updateWeights(AbstractNeuron neuron, double delta, double momentumFactor) {
        List<NeuronInput> inputNeurons = neuron.getInputNeurons();
        for (int i = 0; i < inputNeurons.size(); ++i) {
            NeuronInput currentInput = inputNeurons.get(i);
            double currentWeight = currentInput.getWeight();
            double previousWeight = currentInput.getPreviousWeight();
            currentInput.setWeight(currentWeight + delta * currentInput.getInputNeuron().getOutput()
                    + momentumFactor * (currentWeight - previousWeight));
        }
    }
}
