package sise.pietnastka;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * @author PiotrGrzelak
 */
public class BreadthFirstSearch extends AbstractSearch {
    
    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {

        if (initial.equals(goal)) {
            return new Solution(initial, goal);
        }

        Queue<PuzzleNode> open = new LinkedList<>(); 
        open.add(new PuzzleNode(initial));

        Set<PuzzleNode> closed = new HashSet<>(); 
        while (!open.isEmpty()) {
            PuzzleNode node = open.remove();
            closed.add(node);

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);
                numMoves++;

                if (closed.contains(successor)) {
                    continue;
                }

                successor.setTransition(new Transition(move, node, 0));
                if (successor.equals(goal)) {
                    numOpen = open.size();
                    numClosed = closed.size(); 

                    return new Solution(initial, successor);
                }
                open.add(successor);
            }
        }

        numOpen = open.size();
        numClosed = closed.size();  

        return new Solution(initial, goal, false);
    }
}
