package ch.hslu.enapp.h12.tafleisc.boundary.exceptions;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class InvalidProductException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidProductException</code> without detail message.
     */
    public InvalidProductException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidProductException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidProductException(String msg) {
        super(msg);
    }
}
