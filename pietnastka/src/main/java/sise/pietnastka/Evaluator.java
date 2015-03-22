package sise.pietnastka;

/**
 * Better evaluation function, as inspired from Nilsson, p. 66., and applied to
 * the Fifteen Puzzle
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Evaluator {

    private PuzzleNode goal;

    /**
     * index values [r][c] for proper locations, based on goal above.
     */
    private int[][] positionsOfNumbers = {
        {3, 3}, // who cares about the blank? But here for consistency
        {0, 0}, // location where 1 belongs [0][0]
        {0, 1},
        {0, 2},
        {0, 3},
        {1, 0},
        {1, 1},
        {1, 2},
        {1, 3},
        {2, 0},
        {2, 1},
        {2, 2},
        {2, 3},
        {3, 0},
        {3, 1},
        {3, 2}};

    /**
     * Location of the successor for each position, by location. We can take
     * 4*c+r as the index into succs and use that to generate the (r,c) of the
     * successive locationclockwise around the central square.
     */
    private int[][] successors = {
        {0, 1}, /* 0: top middle-left.     0123        */
        {0, 2}, /* 1: top middle-right.    4567        */
        {0, 3}, /* 2: top corner.          8901        */
        {1, 3}, /* 3: right upper          234         */
        {0, 0}, /* 4: upper left                       */
        {-1, -1}, /* 5: no successor.                    */
        {-1, -1}, /* 6: no successor.                    */
        {2, 3}, /* 7: left middle.                     */
        {1, 0}, /* 8: left upper                       */
        {-1, -1}, /* 9: no successor                     */
        {-1, -1}, /* 0: no successor                     */
        {3, 3}, /* 1: bottom corner                    */
        {2, 0}, /* 2: left lower.                      */
        {3, 0}, /* 3: lower corner                     */
        {3, 1}, /* 4: lower left.                      */
        {3, 2} /*  : lower right.                     */};

    /**
     * successors of individual tiles [deal with 15->1 successor evenly.]
     */
    private int[] succ = {
        0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 1 
    };

    public Evaluator(int m, int n) {
        int[][] goalArray = new int[m][];
        
        for (int i = 0; i < goalArray.length; ++i) {
            goalArray[i] = new int[n];
            for (int j = 0; j < n; ++j) {
                goalArray[i][j] = n * i + j + 1;
            }
        }
        goalArray[m - 1][n - 1] = 0;
        goal = new PuzzleNode(goalArray);
        
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

    /**
     * h^(n) = P(n) + 4*S(n), where P(n) is the sum of the distances (via
     * manhattan metric) that each tile is from "home". S(n) is a sequence score
     * obtained by checking around the noncentral squares in turn, allotting 2
     * for every tile not followed by its proper successor and 0 for every other
     * tile, except that a piece in the center scores 1.
     * <p>
     * Compute f^(n) = g^(n) + h^(n) where g(n) is the length of the path form
     * the start node n to the state.
     * <p>
     * @see algs.model.searchtree.IScore#score(INode)
     * @param state state being evaluated
     */
    public int evaluate(PuzzleNode state) {
        PuzzleNode node = (PuzzleNode) state;

        // Each tile is between 0 and 4 moves away from its proper position.
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

        // Kawałek odpowiedzialny za obliczanie tej trochę nie zrozumiałej dla mnie części heurystyki
        // to co powyżej to zwykły Manhaatan
        
		// Compute S(N) by adding 2 points for every tile not followed by its proper successor
        // note that center tile square counts 1.
//        int Sn = 0;
//        if (!node.isEmpty(1, 1)) {
//            Sn = 1;
//        }
//
//        for (int row = 0; row <= PuzzleNode.MaxRowNumber; row++) {
//            for (int column = 0; column <= PuzzleNode.MaxColumnNumber; column++) {
//                int idx = row * 4 + column;
//
//                // skip central squares
//                if ((row == 1 && (column == 1 || column == 2))
//                        || (row == 2 && (column == 1 || column == 2))) {
//                    continue;
//                }
//
//                // skip the empty tile.
//                if (node.getCell(row, column) == 0) {
//                    continue;
//                }
//
//                // create array to deal with successor of 8->1
//                if (succ[node.getCell(row, column)] != node.getCell(successors[idx][0], successors[idx][1])) {
//                    Sn += 2;
//                }
//            }
//        }

        // compute g^(n)
        int gn = 0;
        Transition transition = state.getTransition();
        if (transition != null) {
            gn = transition.getDepth();
        }

        return gn + Pn;// + 4 * Sn;
    }
}
