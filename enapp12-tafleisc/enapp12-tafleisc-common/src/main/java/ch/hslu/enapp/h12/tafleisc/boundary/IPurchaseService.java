package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Payment;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Purchase;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.PaymentFailedException;
import java.util.Collection;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public interface IPurchaseService {

    void addProductToBasket(String productId, int quantity)
            throws InvalidProductException, InvalidQuantityException;

    Collection<PurchaseItem> getItemsInBasket();

    Payment checkout(Payment purchase)
            throws PaymentFailedException;
    
    Collection<Purchase> getCustomerPurchases(int customerId);
}
