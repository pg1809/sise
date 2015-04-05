package sise.pietnastka;

/**
 *
 * @author PiotrGrzelak
 */
public abstract class AbstractSearch {

    protected int numMoves = 0;
    protected int numOpen = 0;
    protected int numClosed = 0;
    
    public abstract Solution search(PuzzleNode initial, PuzzleNode target, String movesOrder);
}
