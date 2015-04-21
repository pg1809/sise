package sise.pietnastka.solver.evaluator;

import sise.pietnastka.solver.PuzzleNode;

/**
 *
 * @author Wojciech Szałapski
 */
public class MisplacedEvaluator implements Evaluator {

    /**
     * Liczba wierszy.
     */
    private int m;

    /**
     * Liczba kolumn.
     */
    private int n;

    public MisplacedEvaluator(int m, int n) {
        this.m = m;
        this.n = n;
    }

    @Override
    public void giveScoreToState(PuzzleNode state) {
        int score = 0;

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                // jeżeli nie jesteśmy na ostatniej pozycji, gdzie powinno być
                // puste miejsce, to sprawdzamy, czy klocek jest na właściwym miejscu
                if ((i != m - 1 || j != n - 1) && state.getCell(i, j) != i * n + j + 1) {
                    ++score;
                }
            }
        }

        state.setScore(score);
    }
}
