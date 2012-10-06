package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import java.util.Collection;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public interface IPurchaseService {

    void addProductToBasket(int productId, int quantity)
            throws InvalidProductException, InvalidQuantityException;

    Collection<PurchaseItem> getItemsInBasket();

    void checkout(int customerId);
}
