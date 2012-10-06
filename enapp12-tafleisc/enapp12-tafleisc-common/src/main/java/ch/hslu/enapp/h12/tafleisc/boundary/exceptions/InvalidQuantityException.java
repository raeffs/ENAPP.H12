package ch.hslu.enapp.h12.tafleisc.boundary.exceptions;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class InvalidQuantityException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidQuantityException</code> without detail message.
     */
    public InvalidQuantityException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidQuantityException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidQuantityException(String msg) {
        super(msg);
    }
}
