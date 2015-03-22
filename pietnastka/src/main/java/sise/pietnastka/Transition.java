package sise.pietnastka;

/**
 * Stores the move and the previous state that was present when the move was
 * made.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Transition {

    private final Move move;

    private final PuzzleNode previous;

    private final int depth;
    
    /**
     * Record the move and previous state of this transition.
     *
     * @param move Move which caused the transition
     * @param prev The previous board state
     */
    public Transition(Move move, PuzzleNode prev, int depth) {
        this.move = move;
        this.previous = prev;
        this.depth = depth;
    }

    Move getMove() {
        return move;
    }

    public PuzzleNode getPrevious() {
        return previous;
    }

    public int getDepth() {
        return depth;
    }
}
