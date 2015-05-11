package sise.network.exceptions;

/**
 *
 * @author Wojciech Szałapski
 */
public class NoStrategySpecifiedException extends CannotCreateNetworkException {

    /**
     * Creates a new instance of <code>NoStrategySpecifiedException</code>
     * without detail message.
     */
    public NoStrategySpecifiedException() {
    }

    /**
     * Constructs an instance of <code>NoStrategySpecifiedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoStrategySpecifiedException(String msg) {
        super(msg);
    }
}
