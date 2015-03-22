package sise.pietnastka;

import java.util.ArrayList;
import java.util.List;


public class Solution {

    /**
     * Initial node.
     */
    private final PuzzleNode initial;

    /**
     * Goal node.
     */
    private final PuzzleNode goal;

    /**
     * Moves that produce the sequence from initial to goal.
     */
    private List<Move> moves;

    /**
     * Was this a successful search?
     */
    private boolean success;

    /**
     * Build the solution and work backwards with a debugger.
     *
     * @param initial initial state
     * @param goal final state
     */
    public Solution(PuzzleNode initial, PuzzleNode goal) {
        this.initial = initial;
        this.goal = goal;

        solve();
        this.success = true;
    }

    /**
     * Build the solution and work backwards without a debugger.
     *
     * @param initial initial state
     * @param goal final state
     * @param success was this a successful search?
     */
    public Solution(PuzzleNode initial, PuzzleNode goal, boolean success) {
        this(initial, goal);
        this.success = success;
    }

    /**
     * Sequence of moves that achieve the goalState.
     *
     * @return
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Was this a successful solution?
     * @return 
     */
    public boolean hasSucceeded() {
        return success;
    }

    /**
     * Number of moves in the solution.
     */
    public int getMovesNum() {
        return moves.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        boolean endsWithComma = false;
        for (Move move : moves) {
            sb.append(move).append("'");
            endsWithComma = true;
        }

        if (endsWithComma) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Generate the solution for the search by working backwards to initial goal
     * and then regenerating in forward order.
     */
    private void solve() {
        PuzzleNode node = goal;

        // Regenerate the trail of nodes into a DoubleLinkedList. 
        List<Move> list = new ArrayList<>();

        // work our way backwards until we terminate at the initial state.
        while (node != null) {
            Transition trans = node.getTransition();

            // gone to the end!
            if (trans == null) {
                break;
            }

            list.add(trans.getMove());
            node = trans.getPrevious();
        }

        // List is now the reverse of the solution path. So we reverse it here
        moves = new ArrayList<>();
        while (!list.isEmpty()) {
            moves.add(list.remove(list.size() - 1));
        }
    }

}
