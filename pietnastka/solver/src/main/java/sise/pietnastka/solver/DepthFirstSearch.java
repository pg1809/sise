package sise.pietnastka.solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Klasa reprezentująca algorytm DFS.
 *
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
        statesOpen = 1;

        statesClosed = 0;
        HashMap<String, PuzzleNode> closed = new HashMap<>();
        while (!open.isEmpty()) {
            PuzzleNode node = open.pop();
            closed.put(node.getStringHash(), node);
            statesClosed++;

            Transition trans = node.getTransition();

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);

                if (closed.get(successor.getStringHash()) != null) {
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

    public void setDepthBound(int depthBound) {
        this.depthBound = depthBound;
    }
}
