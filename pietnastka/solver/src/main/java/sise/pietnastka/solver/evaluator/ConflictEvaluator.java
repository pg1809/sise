package sise.pietnastka.solver.evaluator;

import sise.pietnastka.solver.PuzzleNode;

/**
 * Heurystyka liniowej kolizji.
 *
 * Każdy liniowy konflikt (dwie wartości zamienione miejscami w jednym wierszu)
 * dodaje 2 do wyniku heurystyki Manhattan.
 */
public class ConflictEvaluator extends ManhattanEvaluator {

    /**
     * Liczba wierszy.
     */
    private int m;

    /**
     * Liczba kolumn.
     */
    private int n;

    public ConflictEvaluator(int m, int n) {
        super(m, n);
        this.m = m;
        this.n = n;
    }

    @Override
    protected int evaluate(PuzzleNode state) {
        int manhattanHeuristic = super.evaluate(state);

        int linearCollisions = 0;

        // poniżej rozpatrywany jest docelowy (rozwiązany) układ
        // dla każdego wiersza
        for (int row = 0; row < m; ++row) {
            // weź wszystkie pola z tego wiersza poza ostatnim polem
            for (int f1 = row * n; f1 < (row + 1) * n - 1; ++f1) {
                // dla każdego pola f1 w tym wierszu weź wszystkie pola po prawej stronie
                for (int f2 = f1 + 1; f2 < (row + 1) * n; ++f2) {
                    // należy pamiętać, że ostatnia wartość w ostatnim wierszu jest równa 0
                    if (f2 == m * n) {
                        f2 = 0;
                    }

                    // pobierz faktyczne położenie dwóch rozpatrywanych wartości
                    int f1Column = positionsOfNumbers[f1][1];
                    int f2Column = positionsOfNumbers[f2][1];

                    // sprawdź, czy wartości f1 i f2 nie są w bieżącym wierszu zamienione miejscami
                    if ((f1 < f2) != (f1Column < f2Column)) {
                        ++linearCollisions;
                    }
                }
            }
        }

        // każdy liniowy konflikt dodaje co najmniej 2 ruchy do wyniku danego przez heurystykę Manhattan
        return manhattanHeuristic + 2 * linearCollisions;
    }
}
