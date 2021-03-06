package sise.pietnastka.solver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa, której obiekty przechowują informację o rozwiązaniu układanki, 
 * tj. stan początkowy układanki i sekwencję ruchów jaką należy wykonać w celu rozwiązania układanki
 */
public class Solution implements Serializable {

    private static final long serialVersionUID = -2743513466230183537L;    
    
    private final PuzzleNode initial;

    private final List<Move> moves;

    /**
     * Tworzy nowy obiekt rozwiązania
     *
     * @param initial startowy stan układanki
     * @param goal docelowy stan układanki, potrzebny dla odtworzenia sekwencji posunięć
     */
    public Solution(PuzzleNode initial, PuzzleNode goal) {
        this.initial = initial;
        this.moves = new ArrayList<>();
        recreateMovesSequence(goal);
    }

    public List<Move> getMoves() {
        return moves;
    }
    
    public PuzzleNode getNode() {
        return new PuzzleNode(initial);
    }

    public int getPuzzleRowsNum() {
        return initial.getRowsNum();
    }
    
    public int getPuzzleColumnsNum() {
        return initial.getColumnsNum();
    }
    
    public int getMovesNum() {
        return moves.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Puzzle[")
          .append(initial.getRowsNum())
          .append(" x ")
          .append(initial.getColumnsNum())
          .append("] Solution: ");
        moves.stream().forEach(sb::append);
        return sb.toString();
    }

    /**
     * Odtwarza sekwencję ruchów prowadzących do rozwiązania układanki, robi to "wstecz",
     * wychodząc od stanu docelowego (tak jak w tych wsszystkich algorytmach grafowych, 
     * gdzie ścieżki w grafach musimy odtwarzać na podstawie końcowego wierzchołka)
     */
    private void recreateMovesSequence(PuzzleNode goal) {
        PuzzleNode node = goal;

        while (node != null) {
            Transition trans = node.getTransition();

            if (trans == null) {
                break;
            }

            moves.add(trans.getMove());
            node = trans.getPrevious();
        }

        Collections.reverse(moves);
    }
}
