package sise.pietnastka.solver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wojciech Szałapski
 */
public class MultiSolver {

    private final static int MAX_DEPTH = 20;

    private final static Logger logger = Logger.getLogger(MultiSolver.class.getName());

    public static void main(String[] args) {
        AbstractSearch strategy = null;

        int rows = Integer.parseInt(args[0]);
        int columns = Integer.parseInt(args[1]);

        switch (args[2]) {
            case "-b":
            case "--bfs":
                strategy = new BreadthFirstSearch();
                break;
            case "-d":
            case "--dfs":
                strategy = new DepthFirstSearch(MAX_DEPTH);
                break;
            case "-i":
            case "--idfs":
                strategy = new IterativeDeepeningSearch(MAX_DEPTH);
                break;
            case "-a":
            case "--a":
                if (!args[3].equals("1")) {
                    logger.log(Level.SEVERE, "Niepoprawny identyfikator strategii: {0}", args[1]);
                    return;
                }

                strategy = new AStarSearch();

                switch (args[4]) {
                    case "1":
                        ((AStarSearch) strategy).setScoringFunction(new Evaluator(rows, columns));
                        break;
                    case "2":
                        ((AStarSearch) strategy).setScoringFunction(new CollisionEvaluator(rows, columns));
                        break;
                    default:
                        logger.log(Level.SEVERE, "Niepoprawny identyfikator heurystyki: {0}", args[4]);
                        return;
                }
                break;
        }

        File puzzlesDirectory = new File("D:\\Studia\\SISE\\puzzles\\" + rows + "x" + columns);
        File[] puzzles = puzzlesDirectory.listFiles();
        if (puzzles == null) {
            logger.log(Level.SEVERE, "Couldn''t list files from directory: {0}", puzzlesDirectory.getAbsoluteFile());
            return;
        }
        Arrays.sort(puzzles);

        File solutionsDirectory = new File("D:\\Studia\\SISE\\solutions\\" + rows + "x" + columns);

        for (File puzzle : puzzles) {
            System.out.println(puzzle.getName());

            FileOutputStream outputInfo = null;
            try {
                outputInfo = new FileOutputStream(new File(solutionsDirectory.getAbsoluteFile() + "\\" + puzzle.getName() + ".log"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MultiSolver.class.getName()).log(Level.SEVERE, null, ex);
            }

            PrintWriter writer = new PrintWriter(outputInfo);

            Scanner scanner;
            try {
                scanner = new Scanner(new FileInputStream(puzzle), "UTF-8");
            } catch (FileNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
                return;
            }

            List<Solution> solutions = new ArrayList<>();
            while (scanner.hasNext()) {
                rows = scanner.nextInt();
                columns = scanner.nextInt();

                PuzzleNode goalNode = Fifteen.createGoalNode(rows, columns);
                PuzzleNode startingNode = Fifteen.readStartingNode(rows, columns, scanner);

                Solution solution = strategy.search(startingNode, goalNode, "R");
                if (solution != null) {
                    solutions.add(solution);
                    solution.getMoves().forEach(System.out::print);
                    System.out.println();
                } else {
                    System.out.println(-1);
                }

                writer.append(strategy.getStatesOpen() + " " + strategy.getStatesClosed() + " " + strategy.getMaximumDepth() + "\n");
            }

            writer.flush();

            File solutionFile = new File(solutionsDirectory.getAbsoluteFile() + "\\" + puzzle.getName());
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(solutionFile))) {
                out.writeObject(solutions);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
