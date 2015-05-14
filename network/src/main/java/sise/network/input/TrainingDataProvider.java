package sise.network.input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sise.network.normalization.NumericInputNormalizer;

/**
 *
 * @author Wojciech Szałapski
 */
public class TrainingDataProvider implements InputProvider {

    protected List<InputRow> dataset;

    private int nextRow = 0;

    public TrainingDataProvider(File inputFile, int inputs, int outputs,
            String separator, NumericInputNormalizer normalizer) throws IOException {
        Scanner sc = new Scanner(inputFile);

        dataset = new ArrayList<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] stringNums = line.split(separator);

            double[] inputValues = new double[inputs];
            for (int i = 0; i < inputs; ++i) {
                inputValues[i] = normalizer.normalize(Double.parseDouble(stringNums[i]));
            }

            double[] outputValues = new double[outputs];
            for (int i = 0; i < outputs; ++i) {
                outputValues[i] = Double.parseDouble(stringNums[inputs + i]);
            }

            dataset.add(new InputRow(inputValues, outputValues));
        }
    }

    @Override
    public boolean hasMoreRows() {
        return (nextRow < dataset.size());
    }

    @Override
    public InputRow provideInputRow() {
        return dataset.get(nextRow++);
    }

    @Override
    public List<InputRow> provideAllRows() {
        List<InputRow> result = new ArrayList<>();

        while (nextRow < dataset.size()) {
            result.add(dataset.get(nextRow++));
        }

        return result;
    }
}
