package sise.pietnastka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleNode {

    private int[][] board;

    private int score;

    private Transition transition;

    private static Map<Character, int[]> deltas;

    private static List<int[]> deltasList = new ArrayList<>(4);

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

    public List<Move> getValidMoves(String movesOrder) {
        List<Move> list = new ArrayList<>();

        // where is the blank?
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

    public boolean isAdjacentAndEmpty(int fromRow, int fromColumn, int toRow, int toColumn) {

        if (board[toRow][toColumn] != 0) {
            return false;
        }

        // swap if adjacent via manhattan directions (no diagonals!)
        int dC = Math.abs(fromRow - toRow);
        int dR = Math.abs(fromColumn - toColumn);
        return (dC == -1 && dR == 0)
                || (dC == 1 && dR == 0)
                || (dC == 0 && dR == -1)
                || (dC == 0 && dR == 1);
    }

    public boolean swap(int fromRow, int fromColumn, int toRow, int toColumn) {
        if (!isAdjacentAndEmpty(fromRow, fromColumn, toRow, toColumn)) {
            return false;
        }

        int tmp = board[toRow][toColumn];
        board[toRow][toColumn] = board[fromRow][fromColumn];
        board[fromRow][fromColumn] = tmp;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof PuzzleNode) {
            return equivalent((PuzzleNode) o);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return key().hashCode();
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) {
                    sb.append("   ");
                } else {
                    if (board[r][c] < 10) {
                        sb.append(" ");
                    }
                    sb.append(board[r][c]).append(" ");
                }
            }
            sb.append('\n');
        }

        return sb.toString();
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

    private boolean equivalent(PuzzleNode n) {
        if (n == null) {
            return false;
        }

        PuzzleNode state = (PuzzleNode) n;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] != state.board[row][column]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Return key that satisfies rotational symmetry.
     *
     * Considering the four corners of the board, select the lowest digit and
     * then read off the remaining eight positions in a fixed order as an
     * integer. Return that value.
     */
    private String key() {
        int dr = 1;
        int dc = 1;
        int offRL = 1;
        int offRH = 2;
        int offCL = 1;
        int offCH = 2;
        boolean rFirst = true;
        int d = board[0][0];
        if (board[0][board[0].length - 1] < d) {
            dr = 1;
            dc = -1;
            offCL = 2;
            offCH = 1;
            d = board[0][board[0].length - 1];
            rFirst = false;
        }
        if (board[board.length - 1][board[0].length - 1] < d) {
            dr = -1;
            dc = -1;
            offCL = 2;
            offCH = 1;
            offRL = 2;
            offRH = 1;
            d = board[board.length - 1][board[0].length - 1];
            rFirst = true;
        }
        if (board[board.length - 1][0] < d) {
            dr = -1;
            dc = +1;
            offRL = 2;
            offRH = 1;
            rFirst = false;
        }

        StringBuilder sb = new StringBuilder(10);
        if (rFirst) {
            for (int r = -dr + offRL; dr * r <= dr + offRH; r += dr) {
                for (int c = -dc + offCL; dc * c <= dc + offCH; c += dc) {
                    sb.append(board[r][c]);
                }
            }
        } else {
            for (int c = -dc + offCL; dc * c <= dc + offCH; c += dc) {
                for (int r = -dr + offRL; dr * r <= dr + offRH; r += dr) {
                    sb.append(board[r][c]);
                }
            }
        }

        return sb.toString();
    }
}
