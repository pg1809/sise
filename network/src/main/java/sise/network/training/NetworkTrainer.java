package sise.network.training;

import sise.network.AbstractNetwork;
import sise.network.input.InputRow;
import sise.network.layer.NeuronLayer;
import sise.network.neuron.AbstractNeuron;
import sise.network.neuron.NeuronInput;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Wojciech Szałapski
 */
public abstract class NetworkTrainer {

    private final static double DEFAULT_LEARNING_RATE = 0.1;

    private final static double DEFAULT_MOMENTUM_FACTOR = 0;

    protected double learningRate = DEFAULT_LEARNING_RATE;

    protected double momentumFactor = DEFAULT_MOMENTUM_FACTOR;

    public abstract List<Double> trainNetwork(AbstractNetwork network, List<InputRow> trainingData);

    protected double trainNetworkWithSampleSet(AbstractNetwork network, List<InputRow> trainingData) {
        for (InputRow trainingDataSample : trainingData) {
            trainNetworkWithSample(network, trainingDataSample.getExpectedOutput(), trainingDataSample.getValues());
        }

        double meanSquaredError = 0;
        for (InputRow trainingDataSample : trainingData) {
            double sampleError = 0;
            double[] output = network.runNetwork(trainingDataSample.getValues());

            for (int i = 0; i < output.length; ++i) {
                sampleError += Math.pow(trainingDataSample.getExpectedOutput()[i] - output[i], 2);
            }
            sampleError /= 2;
            sampleError /= output.length;

            meanSquaredError += sampleError;
        }

        meanSquaredError /= trainingData.size();
        return meanSquaredError;
    }

    protected void trainNetworkWithSample(AbstractNetwork network, double[] expectedOutput, double[] sample) {
        network.runNetwork(sample);
        network.getOutputLayer().updateDelta(expectedOutput, learningRate);

        List<NeuronLayer> hiddenLayers = network.getHiddenLayers();
        for (int i = hiddenLayers.size() - 1; i >= 0; --i) {
            hiddenLayers.get(i).updateDelta(null, learningRate);
        }

        for (NeuronLayer hiddenLayer : hiddenLayers) {
            hiddenLayer.updateParameters(momentumFactor);
        }

        network.getOutputLayer().updateParameters(momentumFactor);
    }

    protected void generateStartingWeights(AbstractNetwork network) {
        Random random = new Random();
        generateStartingWeightsForLayer(network.getInputLayer(), random);

        network.getHiddenLayers().stream()
                .forEach((NeuronLayer hiddenLayer) -> generateStartingWeightsForLayer(hiddenLayer, random));
        
        generateStartingWeightsForLayer(network.getOutputLayer(), random);
    }

    private void generateStartingWeightsForLayer(NeuronLayer layer, Random random) {
        for (AbstractNeuron neuron : layer.getNeurons()) {
            neuron.getInputNeurons().stream()
                    .forEach((NeuronInput input) -> input.setWeight(random.nextDouble() - 0.5));
            neuron.setBias(random.nextDouble() - 0.5);
        }
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}
