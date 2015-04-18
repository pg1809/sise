/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 */
public class PuzzlesGenerator {

    private static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public List<int[][]> generatePuzzlesForDistance(int rows, int columns, int distance, int limit) {
        List<int[][]> puzzles = new ArrayList<>();
        StateNode initial = generateInitialState(rows, columns);

        if (distance == 0) {
            puzzles.add(initial.state);
            return puzzles;
        }

        Queue<StateNode> nodesQueue = new LinkedList<>();
        Set<StateNode> processedStates = new HashSet<>();
        nodesQueue.add(initial);

        int statesInList = 0;
        while (!nodesQueue.isEmpty()) {
            StateNode node = nodesQueue.poll();
            processedStates.add(node);

            if (node.distanceFromSolution == distance) {
                puzzles.add(node.state);
                statesInList++;
                if (statesInList == limit) {
                    return puzzles;
                }
            } else if (node.distanceFromSolution < distance) {
                List<StateNode> neighbours = generateNeighbouringStates(node);
                for (StateNode neighbour : neighbours) {
                    if (!processedStates.contains(neighbour)) {
                        nodesQueue.add(neighbour);
                    }
                }
            }
        }

        return puzzles;
    }

    private StateNode generateInitialState(int m, int n) {
        int[][] goalArray = new int[m][];

        for (int i = 0; i < goalArray.length; ++i) {
            goalArray[i] = new int[n];
            for (int j = 0; j < n; ++j) {
                goalArray[i][j] = n * i + j + 1;
            }
        }
        goalArray[m - 1][n - 1] = 0;

        return new StateNode(goalArray, 0);
    }

    private List<StateNode> generateNeighbouringStates(StateNode state) {
        int blankRow = -1, blankColumn = -1;

        boolean finished = false;
        int[][] stateArray = state.state;
        for (int r = 0; r < stateArray.length && !finished; r++) {
            for (int c = 0; c < stateArray[0].length; c++) {
                if (stateArray[r][c] == 0) {
                    blankRow = r;
                    blankColumn = c;
                    finished = true;
                    break;
                }
            }
        }

        List<StateNode> neighbours = new ArrayList<>();

        for (int[] delta : deltas) {
            if (delta[0] + blankRow >= 0 && delta[0] + blankRow < stateArray.length
                    && delta[1] + blankColumn >= 0 && delta[1] + blankColumn < stateArray[0].length) {
                int[][] neighbourArray = copyStateArray(stateArray);
                int tmp = neighbourArray[blankRow][blankColumn];
                neighbourArray[blankRow][blankColumn] = stateArray[blankRow + delta[0]][blankColumn + delta[1]];
                neighbourArray[blankRow + delta[0]][blankColumn + delta[1]] = tmp;

                StateNode neighbour = new StateNode(neighbourArray, state.distanceFromSolution + 1);
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    private int[][] copyStateArray(int[][] state) {
        int[][] copy = Arrays.copyOf(state, state.length);
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = Arrays.copyOf(state[i], state[i].length);
        }
        return copy;
    }

    private class StateNode {

        private final int[][] state;

        private final int distanceFromSolution;

        public StateNode(int[][] state, int distanceFromSolution) {
            this.state = state;
            this.distanceFromSolution = distanceFromSolution;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Arrays.deepHashCode(this.state);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final StateNode other = (StateNode) obj;
            return Arrays.deepEquals(this.state, other.state);
        }
    }

    public static String stateToString(int[][] boardState) {
        StringBuilder builder = new StringBuilder();
        for (int[] row : boardState) {
            for (int cell : row) {
                builder.append(cell).append(" ");
            }
        }

        return builder.toString();
    }
}
