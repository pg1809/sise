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
public abstract class AbstractSearch {

    protected int numMoves = 0;
    protected int numOpen = 0;
    protected int numClosed = 0;
    
    public abstract Solution search(PuzzleNode initial, PuzzleNode target, String movesOrder);
}
