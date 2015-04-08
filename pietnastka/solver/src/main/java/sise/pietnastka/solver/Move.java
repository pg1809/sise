package sise.pietnastka.solver;

import java.io.Serializable;

/**
 * Klasa reprezentuje ruch w grze.
 * 
 * @author PiotrGrzelak
 */
public class Move implements Serializable {

    private static final long serialVersionUID = -7487431982849860203L;
    
    /**
     * Kolumna pola, które jest przesuwane przez ten ruch
     */
    private final int fromColumn;

    /**
     * Rząd pola, które jest przesuwane przez ten ruch
     */
    private final int fromRow;

    /**
     * Docelowa kolumna, do której jest przesuwane pole w wyniku ruchu
     */
    private final int toColumn;

    /**
     * Docelowy rząd pola, do którego jest przesuwane pole w wyniku ruchu
     */
    private final int toRow;

    public Move(int tile, int fromRow, int fromColumn, int toRow, int toColumn) {
        this.fromColumn = fromColumn;
        this.fromRow = fromRow;
        this.toColumn = toColumn;
        this.toRow = toRow;
    }

    /**
     * Wykonuje ruch na podanym stanie planszy
     * @param state stan, na którym ma zostać wykonany ruch
     */
    public void execute(PuzzleNode state) {
        state.swap(fromRow, fromColumn, toRow, toColumn);
    }

    /**
     * Wycofuje ruch na podanym stanie planszy
     * @param state stan, na którym ma zostać wycofany ruch
     */
    public void undo(PuzzleNode state) {
        state.swap(toRow, toColumn, fromRow, fromColumn);
    }

    @Override
    public String toString() {
        if (toRow > fromRow) {
            return "G";
        } else if (toRow < fromRow) {
            return "D";
        }
        if (toColumn > fromColumn) {
            return "L";
        } else if (toColumn < fromColumn) {
            return "P";
        }
        return "";
    }
}
