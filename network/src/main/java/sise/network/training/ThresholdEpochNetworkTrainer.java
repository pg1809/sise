package sise.network.training;

import sise.network.AbstractNetwork;
import sise.network.input.InputRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wojciech Szałapski
 */
public class ThresholdEpochNetworkTrainer extends NetworkTrainer {

    private final int maximumNumberOfEpochs;

    private final double errorThreshold;

    public ThresholdEpochNetworkTrainer(int maximumNumberOfEpochs, double errorThreshold,
            double learningRate, double momentumFactor) {
        this.maximumNumberOfEpochs = maximumNumberOfEpochs;
        this.errorThreshold = errorThreshold;
        this.learningRate = learningRate;
        this.momentumFactor = momentumFactor;
    }

    @Override
    public List<Double> trainNetwork(AbstractNetwork network, List<InputRow> trainingData) {
        generateStartingWeights(network);
        
        List<Double> meanSquaredErrors = new ArrayList<>();

        for (int i = 0; i < maximumNumberOfEpochs; ++i) {
            System.out.println("EPOCH " + i);
            Collections.shuffle(trainingData);

            double error = trainNetworkWithSampleSet(network, trainingData);
            meanSquaredErrors.add(error);

            if (error <= errorThreshold) {
                return meanSquaredErrors;
            }
        }

        return meanSquaredErrors;
    }
}
