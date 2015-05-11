/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.network.input;

import java.util.List;

/**
 *
 * @author Wojciech Szałapski
 */
public interface InputProvider {

    public boolean hasMoreRows();

    public InputRow provideInputRow();
    
    public List<InputRow> provideAllRows();
}
