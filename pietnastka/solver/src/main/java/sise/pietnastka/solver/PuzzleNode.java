package sise.pietnastka.solver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa reprezentująca stan planszy z grą.
 */
public class PuzzleNode implements Serializable {
    
    private static final long serialVersionUID = -5303961755908596552L;

    private final int[][] board;

    private int score;

    private Transition transition;

    private static final Map<Character, int[]> deltas;

    private static final List<int[]> deltasList = new ArrayList<>(4);

    static {
        deltas = new HashMap<>();
        int[] up = {-1, 0};
        int[] down = {1, 0};
        int[] left = {0, -1};
        int[] right = {0, 1};

        deltas.put('L', left);
        deltas.put('P', right);
        deltas.put('G', up);
        deltas.put('D', down);

        deltasList.add(up);
        deltasList.add(down);
        deltasList.add(left);
        deltasList.add(right);
    }

    public PuzzleNode(int[][] b) throws IllegalArgumentException {
        this.board = b;
    }

    public PuzzleNode(PuzzleNode original) {
        int[][] newBoard = new int[original.board.length][original.board[0].length];
        for (int r = 0; r < original.board.length; r++) {
            System.arraycopy(original.board[r], 0, newBoard[r], 0, original.board[0].length);
        }

        board = newBoard;
    }

    /**
     * Zwraca listę wszystkich możliwych do wykonania ruchów w danym stanie. 
     * Lista posortowana jest zgodnie z podanym porządkiem ruchów (np. LDGP, R, DGLP) 
     * @param movesOrder porządek ruchów (zgodnie z treścią zadania)
     * @return lista ruchów, obiektów klasy @link Move
     */
    public List<Move> getValidMoves(String movesOrder) {
        List<Move> list = new ArrayList<>();

        int blankRow = -1, blankColumn = -1;

        boolean finished = false;
        for (int r = 0; r < board.length && !finished; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) {
                    blankRow = r;
                    blankColumn = c;
                    finished = true;
                    break;
                }
            }
        }

        if (movesOrder.startsWith("R")) {
            Collections.shuffle(deltasList);
            for (int[] delta : deltasList) {
                addToMovesListIfValid(list, delta, blankRow, blankColumn);
            }
        } else {
            for (int i = 0; i < movesOrder.length(); ++i) {
                int[] delta = deltas.get(movesOrder.charAt(i));
                addToMovesListIfValid(list, delta, blankRow, blankColumn);
            }
        }
        return list;
    }

    /**
     * Zamienia ze sobą wartości we wskazanych polach
     * @param fromRow rząd pola pierwszego
     * @param fromColumn kolumna pola pierwszego
     * @param toRow rząd pola drugiego
     * @param toColumn kolumna pola drugiego
     */
    public void swap(int fromRow, int fromColumn, int toRow, int toColumn) {
        int tmp = board[toRow][toColumn];
        board[toRow][toColumn] = board[fromRow][fromColumn];
        board[fromRow][fromColumn] = tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (o instanceof PuzzleNode) {
            PuzzleNode state = (PuzzleNode) o;
            return Arrays.deepEquals(state.board, this.board);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.deepHashCode(this.board);
        return hash;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int s) {
        score = s;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public Transition getTransition() {
        return transition;
    }

    public int getCell(int r, int c) {
        return board[r][c];
    }

    public boolean isEmpty(int r, int c) {
        return (board[r][c] == 0);
    }

    public int getRowsNum() {
        return board.length;
    }

    public int getColumnsNum() {
        return board[0].length;
    }

    private void addToMovesListIfValid(List<Move> moves, int[] delta, int blankRow, int blankColumn) {
        int deltaRow = delta[0];
        int deltaColumn = delta[1];
        if (0 <= blankRow + deltaRow && blankRow + deltaRow <= board.length - 1) {
            if (0 <= blankColumn + deltaColumn && blankColumn + deltaColumn <= board[0].length - 1) {
                moves.add(new Move(board[blankRow + deltaRow][blankColumn + deltaColumn],
                        blankRow + deltaRow,
                        blankColumn + deltaColumn,
                        blankRow, blankColumn));
            }
        }
    }
}
