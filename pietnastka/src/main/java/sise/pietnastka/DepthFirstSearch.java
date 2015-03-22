package sise.pietnastka;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch extends AbstractSearch {
    
    protected int depthBound;
   
    public DepthFirstSearch() {
        this(Integer.MAX_VALUE);
    }

    public DepthFirstSearch(int bound) {
        this.depthBound = bound;
    }

    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {

        if (initial.equals(goal)) {
            return new Solution(initial, goal);
        }

        Stack<PuzzleNode> open = new Stack<>();
        open.push(new PuzzleNode(initial));

        Set<PuzzleNode> closed = new HashSet<>(); 
        while (!open.isEmpty()) {
            PuzzleNode node = open.pop();
            closed.add(node);

            Transition trans = node.getTransition();

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);
                numMoves++; 

                if (closed.contains(successor)) {
                    continue;
                }

                int depth = 1;
                if (trans != null) {
                    depth = trans.getDepth() + 1;
                }

                successor.setTransition(new Transition(move, node, depth));
                if (successor.equals(goal)) {
                    numOpen = open.size();
                    numClosed = closed.size(); 

                    return new Solution(initial, successor);
                }
                
                if (depth < depthBound) {
                    open.add(successor);
                }
            }
        }

        numOpen = open.size();
        numClosed = closed.size(); 

        return new Solution(initial, goal, false);
    }

}
