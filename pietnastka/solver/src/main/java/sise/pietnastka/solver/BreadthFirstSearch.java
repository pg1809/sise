package sise.pietnastka.solver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Klasa reprezentująca algorytm BFS.
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

            Transition trans = node.getTransition();

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);

                if (closed.contains(successor)) {
                    continue;
                }

                int depth = 1;
                if (trans != null) {
                    depth = trans.getDepth() + 1;
                }
                maximumDepth = Math.max(maximumDepth, depth);

                successor.setTransition(new Transition(move, node, depth));
                if (successor.equals(goal)) {
                    statesOpen = open.size();
                    statesClosed = closed.size();
                    return new Solution(initial, successor);
                }
                open.add(successor);
            }
        }

        statesOpen = open.size();
        statesClosed = closed.size();
        return null;
    }
}
