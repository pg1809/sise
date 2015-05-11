/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.network.exceptions;

/**
 *
 * @author Wojciech Szałapski
 */
public class CannotCreateNetworkException extends Exception {

    /**
     * Creates a new instance of <code>CannotCreateNetworkException</code>
     * without detail message.
     */
    public CannotCreateNetworkException() {
    }

    /**
     * Constructs an instance of <code>CannotCreateNetworkException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CannotCreateNetworkException(String msg) {
        super(msg);
    }
}
