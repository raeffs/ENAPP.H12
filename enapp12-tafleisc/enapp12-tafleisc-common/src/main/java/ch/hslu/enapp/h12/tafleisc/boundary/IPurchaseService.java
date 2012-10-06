package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public interface IPurchaseService {

    void addProductToBasket(int productId, int quantity)
            throws InvalidProductException, InvalidQuantityException;

    void checkout();
}
