package sise.network.input;

/**
 *
 * @author Wojciech Szałapski
 */
public class InputRow {

    private double[] values;
    
    private double[] expectedOutput;

    public InputRow(double[] values, double[] expectedOutput) {
        this.values = values;
        this.expectedOutput = expectedOutput;
    }
    
    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public double[] getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(double[] expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}
