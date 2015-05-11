package sise.network.factory;

import sise.network.MultiLayerNetwork;
import sise.network.exceptions.CannotCreateNetworkException;
import sise.network.layer.NeuronLayer;
import sise.network.neuron.AbstractNeuron;
import sise.network.neuron.Neuron;
import sise.network.strategy.NeuronStrategy;
import sise.network.strategy.bp.BiasStrategyDecorator;
import java.util.Random;

/**
 *
 * @author Wojciech Szałapski
 */
public class MultiLayerNetworkFactory implements NetworkFactory {
    
    private final int[] neuronsNumberPerLayer;
    
    private final boolean useBias;
    
    private NeuronStrategy strategy;
    
    Random generator = new Random();
    
    public MultiLayerNetworkFactory(int[] neuronsNumberPerLayer, NeuronStrategy strategy, boolean useBias) {
        this.neuronsNumberPerLayer = neuronsNumberPerLayer;
        this.useBias = useBias;
        
        if (useBias) {
            strategy = new BiasStrategyDecorator(strategy);
        }
        this.strategy = strategy;
    }
    
    @Override
    public MultiLayerNetwork createNetwork() throws CannotCreateNetworkException {
        MultiLayerNetwork network = new MultiLayerNetwork();
        
        NeuronLayer inputLayer = createLayerWithNeurons(neuronsNumberPerLayer[0]);
        network.setInputLayer(inputLayer);
        
        int numberOfLayers = neuronsNumberPerLayer.length;
        for (int i = 1; i < numberOfLayers - 1; ++i) {
            network.addHiddenLayer(createLayerWithNeurons(neuronsNumberPerLayer[i]));
        }
        
        NeuronLayer outputLayer = createLayerWithNeurons(neuronsNumberPerLayer[numberOfLayers - 1]);
        network.setOutputLayer(outputLayer);
        
        network.connectAllLayers();
        
        return network;
    }
    
    private NeuronLayer createLayerWithNeurons(int numberOfNeurons) {
        NeuronLayer layer = new NeuronLayer();
        for (int i = 0; i < numberOfNeurons; ++i) {
            AbstractNeuron neuron = new Neuron(strategy);
            if (useBias) {
                neuron.setBias(generator.nextDouble());
                neuron.setPreviousBias(neuron.getBias());
            }
            layer.addNeuron(neuron);
        }
        return layer;
    }
    
    public NeuronStrategy getStrategy() {
        return strategy;
    }
    
    public void setStrategy(NeuronStrategy strategy) {
        this.strategy = strategy;
    }
}
