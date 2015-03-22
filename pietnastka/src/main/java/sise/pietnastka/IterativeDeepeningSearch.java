/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka;

/**
 *
 * @author PiotrGrzelak
 */
public class IterativeDeepeningSearch extends DepthFirstSearch {

    private int maxDepth;
    
    public IterativeDeepeningSearch(int maxDepth) {
        super(1);
        this.maxDepth = maxDepth;
    }

    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {
        Solution solution;
        do {
            solution = super.search(initial, goal, movesOrder);
            depthBound++;
        } while (!solution.hasSucceeded() && depthBound <= maxDepth);
        
        return solution;
    }
}
