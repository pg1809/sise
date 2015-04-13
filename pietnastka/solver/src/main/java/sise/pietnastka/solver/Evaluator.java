package sise.pietnastka.solver;

/**
 * Klasa przeprowadzająca ewaluacje stanu planszy na potrzeby algorytmu A*.
 * Wykorzystana heurystyka to Manhattan.
 * 
 * @author PiotrGrzelak
 */
public class Evaluator {

    protected final int[][] positionsOfNumbers;

    public Evaluator(int m, int n) {
        positionsOfNumbers = new int[m * n][2];
        positionsOfNumbers[0][0] = m - 1;
        positionsOfNumbers[0][1] = n - 1;

        for (int i = 1; i < positionsOfNumbers.length; ++i) {
            positionsOfNumbers[i][0] = (i - 1) / n;
            positionsOfNumbers[i][1] = (i - 1) % n;
        }
    }
    
    /**
     * Przypisuje podanemu stanowi obliczoną liczbę punktów
     * @param state stan do oceny
     */
    public void giveScoreToState(PuzzleNode state) {
        state.setScore(evaluate(state));
    }

    /**
     * Metoda przeprowadzająca obliczenie oceny dla podanego stanu. Wykorzystuje heurystykę Manhattan,
     * Oblicza, dla każdego pola układanki jak daleko znajduje się od miejsca, w którym powinno być, 
     * w docelowym stanie planszy. Odległości te są następnie sumowane. Do oceny dodawana jest 
     * następnie głębokość w drzewie na jakiej znajduje się stan, 
     * liczba ta i suma odległości Manhattan tworzą w sumie ocenę stanu 
     * 
     * @param state stan dla którego liczona jest ocena
     * @return ocena stanu
     */
    protected int evaluate(PuzzleNode state) {
        PuzzleNode node = (PuzzleNode) state;

        int Pn = 0;
        for (int r = 0; r < state.getRowsNum(); r++) {
            for (int c = 0; c < state.getColumnsNum(); c++) {
                if (node.isEmpty(r, c)) {
                    continue;
                }

                int digit = node.getCell(r, c);
                Pn += Math.abs(positionsOfNumbers[digit][0] - r);
                Pn += Math.abs(positionsOfNumbers[digit][1] - c);
            }
        }

        int gn = 0;
        Transition transition = state.getTransition();
        if (transition != null) {
            gn = transition.getDepth();
        }

        return gn + Pn;
    }
}
