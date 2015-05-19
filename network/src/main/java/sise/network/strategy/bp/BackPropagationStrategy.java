package sise.network.strategy.bp;

import sise.network.neuron.AbstractNeuron;
import sise.network.strategy.BasicNeuronStrategy;
import java.util.List;

/**
 *
 * @author Wojciech Szałapski
 */
public class BackPropagationStrategy extends BasicNeuronStrategy {

    protected BackPropagationStrategy() {
    }

    public static BackPropagationStrategy getInstance() {
        return BackPropagationStrategyHolder.INSTANCE;
    }

    private static class BackPropagationStrategyHolder {

        private static final BackPropagationStrategy INSTANCE = new BackPropagationStrategy();
    }

    @Override
    public double transfer(double netValue) {
        return (1 / (1 + Math.exp(-netValue)));
    }

    @Override
    public void updateDelta(AbstractNeuron neuron, Double expectedOutput, double learningRate) {
        double error = 0;

        if (neuron.isOutputNeuron()) {
            error = expectedOutput - neuron.getOutput();
        } else {
            List<AbstractNeuron> forwardNeurons = neuron.getForwardNeurons();
            int forwardConnectionsCount = forwardNeurons.size();
            for (int i = 0; i < forwardConnectionsCount; ++i) {
                error += forwardNeurons.get(i).getDelta() * neuron.findForwardConnectionWeight(i);
            }
        }

        neuron.setDelta(learningRate * error * neuron.getOutput() * (1 - neuron.getOutput()));
    }

    @Override
    public String toString() {
        return "Back propagation";
    }
}
