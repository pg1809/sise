package sise.network.normalization;

/**
 *
 * @author Wojciech Szałapski
 */
public interface NumericInputNormalizer {

    double normalize(double x);
    
    double denormalize(double x);
}
