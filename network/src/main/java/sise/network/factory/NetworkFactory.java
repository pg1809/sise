package sise.network.factory;

import sise.network.AbstractNetwork;
import sise.network.exceptions.CannotCreateNetworkException;

/**
 *
 * @author Wojciech Szałapski
 */
public interface NetworkFactory {

    public AbstractNetwork createNetwork() throws CannotCreateNetworkException;
}
