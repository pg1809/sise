package sise.pietnastka.solver;

/**
 * Klasa zawierająca informacje o przejściu między stanami układanki, tj.
 * jaki był stan układanki przed wykonaniem tego przejścia, 
 * wynikiem jakiego ruchu była ta zmiana stanu oraz głębokość na jakiej doszło do zmiany stanu w drzewie
 * 
 * @author PiotrGrzelak
 */
public class Transition {

    private final Move move;

    private final PuzzleNode previous;

    private final int depth;
    
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
