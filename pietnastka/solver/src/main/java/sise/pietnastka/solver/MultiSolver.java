package sise.pietnastka.solver;

import sise.pietnastka.solver.strategy.IterativeDeepeningSearch;
import sise.pietnastka.solver.strategy.DepthFirstSearch;
import sise.pietnastka.solver.strategy.AbstractSearch;
import sise.pietnastka.solver.strategy.AStarSearch;
import sise.pietnastka.solver.strategy.BreadthFirstSearch;
import sise.pietnastka.solver.evaluator.ManhattanEvaluator;
import sise.pietnastka.solver.evaluator.ConflictEvaluator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sise.pietnastka.solver.evaluator.MisplacedEvaluator;

/**
 *
 * @author Wojciech Szałapski
 */
public class MultiSolver {

    private final static int MAX_DEPTH = 80;

    private final static Logger logger = Logger.getLogger(MultiSolver.class.getName());

    public static void main(String[] args) {
        AbstractSearch strategy = null;

        int rows = Integer.parseInt(args[0]);
        int columns = Integer.parseInt(args[1]);

        String method = "";

        switch (args[2]) {
            case "-b":
            case "--bfs":
                strategy = new BreadthFirstSearch();
                method = "bfs";
                break;
            case "-d":
            case "--dfs":
                if (args.length > 3) {
                    strategy = new DepthFirstSearch(Integer.parseInt(args[3]));
                    method = "dfs" + Integer.parseInt(args[3]);
                } else {
                    strategy = new DepthFirstSearch(MAX_DEPTH);
                    method = "dfs" + MAX_DEPTH;
                }
                break;
            case "-i":
            case "--idfs":
                if (args.length > 3) {
                    strategy = new IterativeDeepeningSearch(Integer.parseInt(args[3]));
                    method = "idfs" + Integer.parseInt(args[3]);
                } else {
                    strategy = new IterativeDeepeningSearch(MAX_DEPTH);
                    method = "idfs" + MAX_DEPTH;
                }
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
                        ((AStarSearch) strategy).setScoringFunction(new ManhattanEvaluator(rows, columns));
                        method = "man";
                        break;
                    case "2":
                        ((AStarSearch) strategy).setScoringFunction(new ConflictEvaluator(rows, columns));
                        method = "linear";
                        break;
                    case "3":
                        ((AStarSearch) strategy).setScoringFunction(new MisplacedEvaluator(rows, columns));
                        method = "misplaced";
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

        File algorithmDirectory = new File(solutionsDirectory.getAbsoluteFile() + "\\" + method);
        if (!algorithmDirectory.mkdir()) {
            logger.log(Level.SEVERE, "Couldn''t create algorithm directory: {0}", algorithmDirectory.getAbsoluteFile());
        }

        for (File puzzle : puzzles) {
            System.out.println(puzzle.getName());

            FileOutputStream summaryOutput = null;
            try {
                summaryOutput = new FileOutputStream(new File(algorithmDirectory.getAbsoluteFile() + "\\" + puzzle.getName() + "-summary.log"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MultiSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintWriter summaryWriter = new PrintWriter(summaryOutput);

            int allPuzzles = 0;
            int solvedPuzzles = 0;
            long openStatesSum = 0;
            long closedStatesSum = 0;
            long maxDepthSum = 0;
            long solutionsOverallLength = 0;
            long overallTime = 0;

            FileOutputStream outputInfo = null;
            try {
                outputInfo = new FileOutputStream(new File(algorithmDirectory.getAbsoluteFile() + "\\" + puzzle.getName() + ".log"));
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
                ++allPuzzles;

                rows = scanner.nextInt();
                columns = scanner.nextInt();

                PuzzleNode goalNode = Fifteen.createGoalNode(rows, columns);
                PuzzleNode startingNode = Fifteen.readStartingNode(rows, columns, scanner);

                long startTime = System.nanoTime();
                Solution solution = strategy.search(startingNode, goalNode, "R");
                long stopTime = System.nanoTime();

                if (solution != null) {
                    solutions.add(solution);
                    ++solvedPuzzles;
                    solutionsOverallLength += solution.getMoves().size();

                    writer.append("" + solution.getMoves().size());
                    solution.getMoves().forEach(System.out::print);
                    System.out.println();
                } else {
                    writer.append("-1");
                    System.out.println(-1);
                }

                openStatesSum += strategy.getStatesOpen();
                closedStatesSum += strategy.getStatesClosed();
                maxDepthSum += strategy.getMaximumDepth();
                overallTime += stopTime - startTime;

                writer.append(" " + strategy.getStatesOpen() + " "
                        + strategy.getStatesClosed() + " "
                        + strategy.getMaximumDepth() + " "
                        + (stopTime - startTime) + "\n");
            }

            writer.flush();

            StringBuilder summary = new StringBuilder();
            summary.append(allPuzzles).append(" ").append(solvedPuzzles).append(" ")
                    .append((double) solutionsOverallLength / solvedPuzzles).append(" ")
                    .append((double) openStatesSum / solvedPuzzles).append(" ")
                    .append((double) closedStatesSum / solvedPuzzles).append(" ")
                    .append((double) maxDepthSum / solvedPuzzles).append(" ")
                    .append((double) overallTime / solvedPuzzles).append("\n");

            summaryWriter.append(summary.toString()).append(" ");
            summaryWriter.flush();

//            File solutionFile = new File(solutionsDirectory.getAbsoluteFile() + "\\" + puzzle.getName());
//            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(solutionFile))) {
//                out.writeObject(solutions);
//            } catch (IOException ex) {
//                logger.log(Level.SEVERE, null, ex);
//            }
        }

    }
}
