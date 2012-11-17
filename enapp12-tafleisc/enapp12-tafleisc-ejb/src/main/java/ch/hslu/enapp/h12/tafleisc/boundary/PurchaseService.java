package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.ProductMapper;
import ch.hslu.enapp.h12.tafleisc.external.dynnav.Item;
import ch.hslu.enapp.h12.tafleisc.control.DynNavFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseitemFacade;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateful
public class PurchaseService implements IPurchaseService {

    @Inject
    private DynNavFacade productFacade;
    @Inject
    private ProductMapper productMapper;
    @Inject
    private PurchaseFacade purchaseFacade;
    @Inject
    private PurchaseitemFacade purchaseitemFacade;
    private Collection<PurchaseItem> basketItems;

    public PurchaseService() {
        basketItems = new ArrayList<PurchaseItem>();
    }

    public PurchaseService(DynNavFacade productFacade,
            Collection<PurchaseItem> collection) {
        this.productFacade = productFacade;
        this.basketItems = collection;
    }

    @Override
    public void addProductToBasket(String productId, int quantity)
            throws InvalidProductException, InvalidQuantityException {
        validateProductId(productId);
        validateQuantity(quantity);
        PurchaseItem purchaseItem = getItemFromBasket(productId);
        if (purchaseItem == null) {
            Item entity = productFacade.find(productId);
            Product product = productMapper.mapEntityToDto(entity);
            addItemToBasket(product, quantity);
        } else {
            purchaseItem.setQuantity(purchaseItem.getQuantity() + quantity);
        }
    }

    private void validateProductId(String productId)
            throws InvalidProductException {
        if (!productFacade.exists(productId)) {
            throw new InvalidProductException();
        }
    }

    private void validateQuantity(int quantity)
            throws InvalidQuantityException {
        if (quantity < 1) {
            throw new InvalidQuantityException();
        }
    }

    private PurchaseItem getItemFromBasket(String productId) {
        for (PurchaseItem purchaseItem : basketItems) {
            if (purchaseItem.getProductId().equals(productId)) {
                return purchaseItem;
            }
        }
        return null;
    }

    private void addItemToBasket(Product product, int quantity) {
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setProductId(product.getProductId());
        purchaseItem.setProductName(product.getName());
        purchaseItem.setQuantity(quantity);
        purchaseItem.setUnitPrice(product.getUnitPrice());
        basketItems.add(purchaseItem);
    }

    @Override
    public void checkout(int customerId) {
        // todo
        basketItems.clear();
    }

    @Override
    public Collection<PurchaseItem> getItemsInBasket() {
        return basketItems;
    }
}
