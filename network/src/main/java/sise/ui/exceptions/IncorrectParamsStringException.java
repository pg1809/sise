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
public class IncorrectParamsStringException extends Exception {

    public IncorrectParamsStringException() {
        super("Niepoprawny format łańcucha numerów cech obiektów");
    }
}
