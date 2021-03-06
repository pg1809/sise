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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class FifteenGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Dystans od rozwiązania: ");
        int distance = scanner.nextInt();
        System.out.print("Limit układanek (ujemna liczba oznacza brak limitu): ");
        int limit = scanner.nextInt();
        System.out.print("Liczba wierszy układanek: ");
        int rows = scanner.nextInt();
        System.out.print("Liczba kolumn układanek: ");
        int columns = scanner.nextInt();

        if (limit < 0) {
            limit = Integer.MAX_VALUE;
        }

        PuzzlesGenerator generator = new PuzzlesGenerator();
        List<int[][]> boardStates = generator.generatePuzzlesForDistance(rows, columns, distance, limit);

        File outputFile = new File("dist" + distance + "rows" + rows + "col" + columns + ".in");
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            for (int[][] boardState : boardStates) {
                writer.println(rows + " " + columns);
                writer.println(PuzzlesGenerator.stateToString(boardState));
            }
        } catch (IOException ex) {
            Logger.getLogger(FifteenGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
