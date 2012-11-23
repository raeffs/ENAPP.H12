package ch.hslu.enapp.h12.tafleisc.boundary.exceptions;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class PaymentFailedException extends Exception {

    /**
     * Creates a new instance of
     * <code>PaymentFailedException</code> without detail message.
     */
    public PaymentFailedException() {
    }

    /**
     * Constructs an instance of
     * <code>PaymentFailedException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PaymentFailedException(String msg) {
        super(msg);
    }
}
