package sise.pietnastka.solver.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wojciech SzaĹ‚apski
 */
public class DataGatherer {

    public static void main(String[] args) {
        String[] methods = new String[]{"bfs", "dfs20", "dfs80", "idfs20", "linear", "man", "misplaced"};

        int rows = 4;
        int columns = 4;

        File solutionsDirectory = new File("D:\\Studia\\SISE\\solutions\\" + rows + "x" + columns);

        for (String method : methods) {
            File algorithmDirectory = new File(solutionsDirectory.getAbsoluteFile() + "\\" + method);
            File[] summaries = algorithmDirectory.listFiles();
            Arrays.sort(summaries);

            File summaryDirectory = new File("D:\\Studia\\SISE\\summary\\" + rows + "x" + columns);
            File summaryFile = new File(summaryDirectory.getAbsoluteFile() + "\\" + method + ".txt");
            try {
                summaryFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DataGatherer.class.getName()).log(Level.SEVERE, null, ex);
            }

            FileWriter writer = null;
            try {
                writer = new FileWriter(summaryFile);
            } catch (IOException ex) {
                Logger.getLogger(DataGatherer.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (File summary : summaries) {
                if (summary.getAbsoluteFile().toString().endsWith("summary.log")) {
                    try (Scanner scanner = new Scanner(summary)) {
                        String line = scanner.nextLine();
                        writer.append(line).append("\n");
                    } catch (IOException ex) {
                        Logger.getLogger(DataGatherer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(DataGatherer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
