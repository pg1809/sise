package sise.pietnastka.solver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Klasa reprezentująca algorytm BFS.
 *
 */
public class BreadthFirstSearch extends AbstractSearch {

    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {

        if (initial.equals(goal)) {
            return new Solution(initial, goal);
        }

        Queue<PuzzleNode> open = new LinkedList<>();
        open.add(new PuzzleNode(initial));
        statesOpen = 1;

        statesClosed = 0;
        Set<PuzzleNode> closed = new HashSet<>();
        while (!open.isEmpty()) {
            PuzzleNode node = open.remove();
            closed.add(node);
            statesClosed++;

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
                    return new Solution(initial, successor);
                }
                open.add(successor);
                ++statesOpen;
            }
        }

        return null;
    }
}
