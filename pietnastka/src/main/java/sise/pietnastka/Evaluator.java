package sise.pietnastka;

public class Evaluator {

    private int[][] positionsOfNumbers;

    public Evaluator(int m, int n) {
        positionsOfNumbers = new int[m * n][2];
        positionsOfNumbers[0][0] = m - 1;
        positionsOfNumbers[0][1] = n - 1;

        for (int i = 1; i < positionsOfNumbers.length; ++i) {
            positionsOfNumbers[i][0] = (i - 1) / n;
            positionsOfNumbers[i][1] = (i - 1) % n;
        }
    }
   
    public void giveScoreToState(PuzzleNode state) {
        state.setScore(evaluate(state));
    }

    public int evaluate(PuzzleNode state) {
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
