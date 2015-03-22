package sise.pietnastka;

/**
 * Slide a numbered tile from (c,r) to (c', r').
 *
 * @author George Heineman
 */
public class Move {

    /**
     * column coordinate of the move's source.
     */
    private final int fromColumn;

    /**
     * row coordinate of the move's source.
     */
    private final int fromRow;

    /**
     * column coordinate of the move's destination.
     */
    private final int toColumn;

    /**
     * row coordinate of the move's destination.
     */
    private final int toRow;

    public Move(int tile, int fromColumn, int fromRow, int toColumn, int toRow) {
        this.fromColumn = fromColumn;
        this.fromRow = fromRow;
        this.toColumn = toColumn;
        this.toRow = toRow;
    }

    public boolean execute(PuzzleNode state) {
        if (state.isAdjacentAndEmpty(fromColumn, fromRow, toColumn, toRow)) {
            return state.swap(fromColumn, fromRow, toColumn, toRow);
        }

        return false;
    }

    public boolean isValid(PuzzleNode state) {
        if (fromColumn < 0 || fromColumn > state.getRowsNum()) {
            return false;
        }
        if (fromRow < 0 || fromRow > state.getColumnsNum()) {
            return false;
        }
        if (toColumn < 0 || toColumn > state.getRowsNum()) {
            return false;
        }
        if (toRow < 0 || toRow > state.getColumnsNum()) {
            return false;
        }

        return state.isAdjacentAndEmpty(fromColumn, fromRow, toColumn, toRow);
    }

    public boolean undo(PuzzleNode state) {
        return state.swap(toColumn, toRow, fromColumn, fromRow);
    }

    @Override
    public String toString() {
        if (toRow > fromRow) {
            return "D";
        } else if (toRow < fromRow) {
            return "G";
        }
        if (toColumn > fromColumn) {
            return "P";
        } else if (toColumn < fromColumn) {
            return "L";
        }
        return "";
    }
}
