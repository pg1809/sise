/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka.generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generator badanych układów.
 */
public class DatasetGenerator {

    private final static Logger logger = Logger.getLogger(DatasetGenerator.class.getName());

    public static void main(String[] args) {
        int rows = 4;
        int columns = 4;

        File sizeDirectory = new File(rows + "x" + columns);
        if (!sizeDirectory.isDirectory()) {
            if (!sizeDirectory.mkdir()) {
                logger.log(Level.SEVERE, "Couldn''t create directory: {0}", sizeDirectory.getAbsoluteFile());
                return;
            }
        }

//        int minDistance = Integer.parseInt(args[0]);
//        int maxDistance = Integer.parseInt(args[1]);
        
        int minDistance = 21;
        int maxDistance = 40;

        for (int distance = minDistance; distance <= maxDistance; ++distance) {
            int limit = 10;
            if (distance <= 6) {
                limit = -1;
            }

            PuzzlesGenerator generator = new PuzzlesGenerator();
            List<int[][]> boardStates = generator.generatePuzzlesForDistance(rows, columns, distance, limit);

            File outputFile = new File(sizeDirectory.getAbsoluteFile() + "\\dist" + distance + "rows" + rows + "col" + columns + ".in");
            try (PrintWriter writer = new PrintWriter(outputFile)) {
                for (int[][] boardState : boardStates) {
                    writer.println(rows + " " + columns);
                    writer.println(PuzzlesGenerator.stateToString(boardState));
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
