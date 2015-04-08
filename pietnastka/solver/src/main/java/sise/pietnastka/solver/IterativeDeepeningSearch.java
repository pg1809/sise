/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka.solver;

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
        // Puszczamy w pętli DFS, zwiększając mu co obieg ograniczenie głębokości
        int numClosed = statesClosed;
        int numOpen = statesOpen;
        do {
            solution = super.search(initial, goal, movesOrder);
            depthBound++;
            numClosed += statesClosed;
            numOpen += statesOpen;
        // Operacje powtarzamy dopóki nie zostanie znalezione rozwiązanie, 
        // albo nie zostanie przekroczona maksymalna głębokość
        } while (solution != null && depthBound <= maxDepth);
        
        depthBound = 1;
        statesOpen = numOpen;
        statesClosed = numClosed;
        return solution;
    }
}
