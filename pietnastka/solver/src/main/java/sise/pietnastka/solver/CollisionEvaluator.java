package sise.pietnastka.solver;

/**
 * Linear collision heuristic.
 * 
 * Each linear collision (two values swapped in one row) adds 2 to the result.
 */
public class CollisionEvaluator extends Evaluator {

    /**
     * Rows.
     */
    private int m;

    /**
     * Columns.
     */
    private int n;

    public CollisionEvaluator(int m, int n) {
        super(m, n);
        this.m = m;
        this.n = n;
    }

    @Override
    protected int evaluate(PuzzleNode state) {
        int manhattanHeuristic = super.evaluate(state);

        int linearCollisions = 0;
        
        // for each row
        for (int row = 0; row < m; ++row) {
            // take all fields from this row apart from the last one
            for (int f1 = row * n; f1 < (row + 1) * n - 1; ++f1) {
                // for all fields which are in this row to the right from f1
                for (int f2 = f1 + 1; f2 < (row + 1) * n; ++f2) {
                    // remember that the last value in last row is 0
                    if (f2 == m * n) {
                        f2 = 0;
                    }
                    
                    int f1Column = positionsOfNumbers[f1][1];
                    int f2Column = positionsOfNumbers[f2][1];
                    
                    // check if f1 and f2 are swapped in current row
                    if ((f1 < f2) != (f1Column < f2Column)) {
                        ++linearCollisions;
                    }
                }
            }
        }
        
        // each linear collision adds at least 2 moves to Manhattan heuristic
        return manhattanHeuristic + 2 * linearCollisions;
    }
}
