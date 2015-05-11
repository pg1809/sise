package sise.ui.plot;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Wojciech Szałapski
 */
public class PlotNamer {

    private String baseName = "plot";

    private Double learningRate;

    private Double momentumFactor;

    private Integer epochs;

    private Boolean isBiasUsed;

    private Integer hiddenNeurons;
    
    private String suffix;

    private final DecimalFormat decimalFormat = new DecimalFormat("#.####");

    public PlotNamer() {
        decimalFormat.setRoundingMode(RoundingMode.UP);
    }

    public String generateName() {
        StringBuilder builder = new StringBuilder(baseName);

        if (hiddenNeurons != null) {
            builder.append(" n = ").append(hiddenNeurons);
        }

        if (epochs != null) {
            builder.append(" ep = ").append(epochs);
        }

        if (learningRate != null) {
            builder.append(" a = ").append(decimalFormat.format(learningRate));
        }

        if (momentumFactor != null) {
            builder.append(" m = ").append(decimalFormat.format(momentumFactor));
        }

        if (isBiasUsed != null) {
            if (isBiasUsed) {
                builder.append(" bias");
            }
        }
        
        if (suffix != null) {
            builder.append(suffix);
        }

        builder.append(".png");

        return builder.toString();
    }

    public PlotNamer setBaseName(String baseName) {
        this.baseName = baseName;
        return this;
    }

    public PlotNamer setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public PlotNamer setMomentumFactor(double momentumFactor) {
        this.momentumFactor = momentumFactor;
        return this;
    }

    public PlotNamer setEpochs(int epochs) {
        this.epochs = epochs;
        return this;
    }

    public PlotNamer setHiddenNeurons(int hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
        return this;
    }
    
    public PlotNamer setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
