package sise.pietnastka.solver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Klasa reprezentująca algorytm DFS.
 *
 * @author PiotrGrzelak
 */
public class DepthFirstSearch extends AbstractSearch {

    /**
     * Maksymalna głębokość na jaką algorytm może zagłebić się w drzewo.
     */
    protected int depthBound;

    public DepthFirstSearch() {
        this(Integer.MAX_VALUE);
    }

    public DepthFirstSearch(int bound) {
        super();
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

                // jeśli nie zeszliśmy jeszcze na maksymalną głębokość,
                // dodajemy nowy stan planszy do zbioru stanów oczekujących na przetowrzenie
                if (depth < depthBound) {
                    open.add(successor);
                }
            }
        }

        statesOpen = open.size();
        statesClosed = closed.size();
        return null;
    }

}
