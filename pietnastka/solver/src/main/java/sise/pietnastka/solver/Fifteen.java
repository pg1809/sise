/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka.solver;

import sise.pietnastka.solver.strategy.IterativeDeepeningSearch;
import sise.pietnastka.solver.strategy.DepthFirstSearch;
import sise.pietnastka.solver.strategy.AbstractSearch;
import sise.pietnastka.solver.strategy.AStarSearch;
import sise.pietnastka.solver.strategy.BreadthFirstSearch;
import sise.pietnastka.solver.evaluator.ManhattanEvaluator;
import sise.pietnastka.solver.evaluator.ConflictEvaluator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import sise.pietnastka.solver.evaluator.MisplacedEvaluator;

/**
 *
 */
public class Fifteen {

    private static final Logger logger = Logger.getLogger(Fifteen.class.getName());

    private static final int DFS_DEPTH_BOUND = 20;

    private static final Map<String, AbstractSearch> algorithmsMap = new HashMap<>();

    static {
        DepthFirstSearch dfs = new DepthFirstSearch(DFS_DEPTH_BOUND);
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        IterativeDeepeningSearch idfs = new IterativeDeepeningSearch(DFS_DEPTH_BOUND);
        AStarSearch astar = new AStarSearch();

        algorithmsMap.put("-b", bfs);
        algorithmsMap.put("--bfs", bfs);
        algorithmsMap.put("-d", dfs);
        algorithmsMap.put("--dfs", dfs);
        algorithmsMap.put("-i", idfs);
        algorithmsMap.put("--idfs", idfs);
        algorithmsMap.put("-a", astar);
        algorithmsMap.put("--a", astar);

        try {
            Handler loggerHandler = new FileHandler("logger.log");
            loggerHandler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);
            logger.addHandler(loggerHandler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Fifteen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Zbyt mała liczba parametrów");
            return;
        }

        AbstractSearch searchAlgorithm = algorithmsMap.get(args[0]);
        if (searchAlgorithm == null) {
            System.out.println("Błędny parametr algorytmu");
            return;
        }

        String order = args[1];

        if (searchAlgorithm instanceof AStarSearch) {
            if (Integer.parseInt(args[1]) != 1) {
                System.out.println("Niepoprawny identyfikator strategii");
                return;
            }

            int heuristicId;
            try {
                heuristicId = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                heuristicId = -1;
            }
            if (heuristicId < 1 || heuristicId > 3) {
                System.out.println("Niepoprawny identyfikator heurystyki");
                return;
            }

            order = "R";
        } else if (!order.matches("[LDGP]{4}|[R]")) {
            System.out.println("Niepoprawna kolejność ruchów");
            return;
        }

        if (args.length == 3) {
            if (searchAlgorithm instanceof DepthFirstSearch) {
                ((DepthFirstSearch) searchAlgorithm).setDepthBound(Integer.parseInt(args[2]));
            } else if (searchAlgorithm instanceof IterativeDeepeningSearch) {
                ((IterativeDeepeningSearch) searchAlgorithm).setMaxDepth(Integer.parseInt(args[2]));
            } else if (!(searchAlgorithm instanceof AStarSearch)) {
                System.out.println("Za duża liczba parametrów");
                return;
            }
        }

        int w;
        int k;
        Scanner scanner = new Scanner(System.in);

        List<Solution> solutions = new ArrayList<>();
        while (scanner.hasNext()) {
            w = scanner.nextInt();
            k = scanner.nextInt();

            if (w <= 0 || k <= 0) {
                break;
            }

            if (searchAlgorithm instanceof AStarSearch) {
                switch (args[2]) {
                    case "1":
                        ((AStarSearch) searchAlgorithm).setScoringFunction(new ManhattanEvaluator(w, k));
                        break;
                    case "2":
                        ((AStarSearch) searchAlgorithm).setScoringFunction(new ConflictEvaluator(w, k));
                        break;
                    case "3":
                        ((AStarSearch) searchAlgorithm).setScoringFunction(new MisplacedEvaluator(w, k));
                        break;
                }
            }

            PuzzleNode goalNode = createGoalNode(w, k);
            PuzzleNode startingNode = readStartingNode(w, k, scanner);

            Solution solution = searchAlgorithm.search(startingNode, goalNode, order);
            if (solution != null) {
                solutions.add(solution);
                solution.getMoves().forEach(System.out::print);
                System.out.println();
            } else {
                System.out.println(-1);
            }

            StringBuilder loggerMsgBuilder = new StringBuilder();
            loggerMsgBuilder.append("Solution: ");
            if (solution != null) {
                solution.getMoves().forEach(loggerMsgBuilder::append);
            } else {
                loggerMsgBuilder.append("-1");
            }
            loggerMsgBuilder.append(" Opened states: ")
                    .append(searchAlgorithm.getStatesOpen()).append(" ")
                    .append("Closed states: ").append(searchAlgorithm.getStatesClosed()).append(" ")
                    .append("Maximum depth: ").append(searchAlgorithm.getMaximumDepth()).append(" ");

            logger.info(loggerMsgBuilder.toString());

        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("output"))) {
            out.writeObject(solutions);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static PuzzleNode createGoalNode(int w, int k) {
        int[][] goalArray = new int[w][];

        for (int i = 0; i < goalArray.length; ++i) {
            goalArray[i] = new int[k];
            for (int j = 0; j < k; ++j) {
                goalArray[i][j] = k * i + j + 1;
            }
        }
        goalArray[w - 1][k - 1] = 0;

        return new PuzzleNode(goalArray);
    }

    public static PuzzleNode readStartingNode(int w, int k, Scanner scanner) {
        int[][] array = new int[w][];

        for (int i = 0; i < array.length; ++i) {
            array[i] = new int[k];
            for (int j = 0; j < k; ++j) {
                array[i][j] = scanner.nextInt();
            }
        }

        return new PuzzleNode(array);
    }
}
