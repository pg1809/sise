package sise.network.normalization;

/**
 *
 * @author Wojciech Szałapski
 */
public class MinMaxInputNormalizer implements NumericInputNormalizer {

    private final double min;
    
    private final double span;

    public MinMaxInputNormalizer(double min, double max) {
        this.min = min;
        span = max - min;
    }
    
    @Override
    public double normalize(double x) {
        return (x - min) / span;
    }
}
