package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import ch.hslu.enapp.h12.tafleisc.control.ProductFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseitemFacade;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseitemEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateful
public class PurchaseService implements IPurchaseService {

    @Inject
    private ProductFacade productFacade;
    @Inject
    private PurchaseFacade purchaseFacade;
    @Inject
    private PurchaseitemFacade purchaseitemFacade;
    private Collection<PurchaseitemEntity> items;

    public PurchaseService() {
        items = new ArrayList<PurchaseitemEntity>();
    }

    public PurchaseService(ProductFacade productFacade,
            Collection<PurchaseitemEntity> collection) {
        this.productFacade = productFacade;
        this.items = collection;
    }

    @Override
    public void addProductToBasket(int productId, int quantity)
            throws InvalidProductException, InvalidQuantityException {
        validateProductId(productId);
        validateQuantity(quantity);
        ProductEntity product = productFacade.find(productId);
        addItemToBasket(product, quantity);
    }

    private void validateProductId(int productId)
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

    private void addItemToBasket(ProductEntity product, int quantity) {
        PurchaseitemEntity item = new PurchaseitemEntity(0);
        item.setProductid(product.getId());
        item.setQuantity(quantity);
        item.setUnitprice(product.getUnitprice());
        item.setLineamount(quantity * product.getUnitprice());
        items.add(item);
    }

    @Override
    public void checkout(int customerId) {
        int purchaseId = createPurchase(customerId);
        savePurchaseitems(purchaseId);
    }

    private int createPurchase(int customerId) {
        PurchaseEntity purchase = new PurchaseEntity(0);
        purchase.setCustomerid(customerId);
        purchase.setDatetime(new Date());
        purchaseFacade.create(purchase);
        return purchase.getId();
    }

    private void savePurchaseitems(int purchaseId) {
        for (PurchaseitemEntity item : items) {
            item.setPurchaseid(purchaseId);
            purchaseitemFacade.create(item);
        }
    }
}
