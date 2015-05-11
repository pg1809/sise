/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.ui.exceptions;

/**
 *
 * @author PiotrGrzelak
 */
public class EmptyInputFieldException extends Exception {

    public EmptyInputFieldException(String fieldLabel) {
        super("Pole " + fieldLabel + " jest puste!");
    }
}
