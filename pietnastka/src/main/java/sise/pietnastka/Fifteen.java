/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka;

/**
 *
 * @author PiotrGrzelak
 */
public class Fifteen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AbstractSearch search = new AStarSearch(new Evaluator(4, 4));
                                //new DepthFirstSearch();
                                //new BreadthFirstSearch();
                                new IterativeDeepeningSearch(20);
        int[][] initialBoard = //{{1, 2, 7, 3}, {5, 10, 6, 4}, {9, 0, 12, 8}, {13, 14, 11, 15}};
                {{2, 6, 3, 4}, {1, 0, 7, 8}, {5, 9, 10, 12}, {13, 14, 11, 15}};
        int[][] goalBoard = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        
        PuzzleNode start = new PuzzleNode(initialBoard);
        PuzzleNode goal = new PuzzleNode(goalBoard);
        
        Solution solution = search.search(start, goal, "R");
        System.out.println("Succ: " + solution.hasSucceeded() + ", moves num: " + solution.getMovesNum());
    }
}
