package sise.network.weight;

/**
 *
 * @author Wojciech Szałapski
 */
public abstract class WeightsGenerator {

    protected int inputs;

    protected WeightsGenerator(int inputs) {
        this.inputs = inputs;
    }

    public abstract double[] generateWeights();

    public int getInputs() {
        return inputs;
    }

    public void setInputs(int inputs) {
        this.inputs = inputs;
    }
}
