/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.pietnastka.solver.evaluator;

import sise.pietnastka.solver.PuzzleNode;

/**
 *
 * @author Wojciech Szałapski
 */
public interface Evaluator {
    
    /**
     * Przypisuje podanemu stanowi obliczoną liczbę punktów
     * @param state stan do oceny
     */
    public void giveScoreToState(PuzzleNode state);
}
