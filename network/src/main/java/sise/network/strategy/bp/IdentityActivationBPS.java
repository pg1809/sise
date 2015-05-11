/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sise.network.strategy.bp;

import sise.network.neuron.AbstractNeuron;
import java.util.List;

/**
 *
 * @author PiotrGrzelak
 */
public class IdentityActivationBPS extends BackPropagationStrategy {
    
    private static final IdentityActivationBPS instance = new IdentityActivationBPS();
    
    public static IdentityActivationBPS getInstance() {
        return instance;
    }
    
    @Override
    public double transfer(double netValue) {
        return netValue;
    }
    
    @Override
    public void updateDelta(AbstractNeuron neuron, Double expectedOutput, double learningRate) {
        double error = 0;
        
        if (neuron.isOutputNeuron()) {
            error = expectedOutput - neuron.getOutput();
        } else {
            List<AbstractNeuron> forwardNeurons = neuron.getForwardNeurons();
            int forwardConnectionsCount = forwardNeurons.size();
            for (int i = 0; i < forwardConnectionsCount; ++i) {
                error += forwardNeurons.get(i).getDelta() * neuron.findForwardConnectionWeight(i);
            }
        }
        
        neuron.setDelta(learningRate * error);
    }
}
