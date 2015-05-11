package sise.network.weight;

import java.util.Random;

/**
 *
 * @author Wojciech Szałapski
 */
public class RandomWeightsGenerator extends WeightsGenerator {

    private double lowerBound;
    
    private double upperBound;
    
    private final Random generator = new Random();

    public RandomWeightsGenerator(int inputs, double lowerBound, double upperBound) {
        super(inputs);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public double[] generateWeights() {
        double[] weights = new double[inputs];
        
        double range = upperBound - lowerBound;
        for (int i = 0; i < inputs; ++i) {
            weights[i] = lowerBound + range * generator.nextDouble();
        }
        
        return weights;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }
}
