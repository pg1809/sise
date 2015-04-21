/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka.solver.strategy;

import java.util.List;
import java.util.Stack;
import sise.pietnastka.solver.Move;
import sise.pietnastka.solver.PuzzleNode;
import sise.pietnastka.solver.Solution;
import sise.pietnastka.solver.Transition;

/**
 *
 */
public class IterativeDeepeningSearch extends AbstractSearch {

    private int maxDepth;
    
    public IterativeDeepeningSearch(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {
        statesOpen = 0;
        statesClosed = 0;
        int depthBound = 1;
        
        Solution solution;
        // Puszczamy w pętli DFS, zwiększając mu co obieg ograniczenie głębokości
        do {
            solution = performSearchToDepth(initial, goal, movesOrder, depthBound);
            depthBound++;
        // Operacje powtarzamy dopóki nie zostanie znalezione rozwiązanie, 
        // albo nie zostanie przekroczona maksymalna głębokość
        } while (solution == null && depthBound <= maxDepth);
        
        return solution;
    }
    
    private Solution performSearchToDepth(PuzzleNode initial, PuzzleNode goal, String movesOrder, int depthBound) {
        
        if (initial.equals(goal)) {
            return new Solution(initial, goal);
        }

        Stack<PuzzleNode> open = new Stack<>();
        open.push(new PuzzleNode(initial));
        statesOpen++;

        while (!open.isEmpty()) {
            PuzzleNode node = open.pop();

            Transition trans = node.getTransition();

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);               

                if (successor.equals(node) || open.contains(successor)) {
                    continue;
                }
                
                int depth = 1;
                if (trans != null) {
                    depth = trans.getDepth() + 1;
                }
                maximumDepth = Math.max(maximumDepth, depth);

                successor.setTransition(new Transition(move, node, depth));
                if (successor.equals(goal)) {
                    return new Solution(initial, successor);
                }

                // jeśli nie zeszliśmy jeszcze na maksymalną głębokość,
                // dodajemy nowy stan planszy do zbioru stanów oczekujących na przetworzenie
                if (depth < depthBound) {
                    open.add(successor);
                    statesOpen++;
                }
            }
        }

        return null;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}
