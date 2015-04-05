/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author PiotrGrzelak
 */
public class PuzzlesGenerator {
    
    private static final int PUZZLE_DISTANCES_NUM = 7;
    
    private static final int MAX_DISTANCE_PUZZLES_LIMIT = 10;
    
    public Map<Integer, PuzzleNode> generatePuzzles(int rows, int columns) {
        Map<Integer, PuzzleNode> puzzles = new HashMap<>();
        
        int maxDistancePuzzlesNum = 0;
        int[][] initialState = generateInitialState(rows, columns);
        puzzles.put(0, new PuzzleNode(initialState));
        
        Queue<int[][]> statesQueue = new LinkedList<>(); 
        statesQueue.add(initialState);
        
        boolean stop = false;
        for (int i = 1; stop && !statesQueue.isEmpty(); ++i) {
            int[][] state = statesQueue.poll();
            List<int[][]> neighbouringStates = generateNeighbouringStates(state);
            
            for (int[][] neighbouringState : neighbouringStates) {
                if (i == 6) {
                    if (maxDistancePuzzlesNum == MAX_DISTANCE_PUZZLES_LIMIT) {
                        stop = true;
                        break;
                    }
                    maxDistancePuzzlesNum++;
                }
                
                statesQueue.add(neighbouringState);
                puzzles.put(i + 1, new PuzzleNode(neighbouringState));
            }
        }
        
        return puzzles;
    }

    private int[][] generateInitialState(int rows, int columns) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<int[][]> generateNeighbouringStates(int[][] state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
