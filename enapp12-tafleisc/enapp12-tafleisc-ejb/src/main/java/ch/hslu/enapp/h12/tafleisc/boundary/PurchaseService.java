package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
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
    private Collection<PurchaseitemEntity> basketItems;

    public PurchaseService() {
        basketItems = new ArrayList<PurchaseitemEntity>();
    }

    public PurchaseService(ProductFacade productFacade,
            Collection<PurchaseitemEntity> collection) {
        this.productFacade = productFacade;
        this.basketItems = collection;
    }

    @Override
    public void addProductToBasket(int productId, int quantity)
            throws InvalidProductException, InvalidQuantityException {
        validateProductId(productId);
        validateQuantity(quantity);
        PurchaseitemEntity item = getItemFromBasket(productId);
        if (item == null) {
            ProductEntity product = productFacade.find(productId);
            addItemToBasket(product, quantity);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
            item.setLineamount(item.getQuantity() * item.getUnitprice());
        }
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

    private PurchaseitemEntity getItemFromBasket(int productId) {
        for (PurchaseitemEntity item : basketItems) {
            if (item.getProductid() == productId) {
                return item;
            }
        }
        return null;
    }

    private void addItemToBasket(ProductEntity product, int quantity) {
        PurchaseitemEntity item = new PurchaseitemEntity(0);
        item.setProductid(product.getId());
        item.setQuantity(quantity);
        item.setUnitprice(product.getUnitprice());
        item.setLineamount(quantity * product.getUnitprice());
        basketItems.add(item);
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
        purchase.setStatus("test");
        purchaseFacade.create(purchase);
        return purchase.getId();
    }

    private void savePurchaseitems(int purchaseId) {
        for (PurchaseitemEntity item : basketItems) {
            item.setPurchaseid(purchaseId);
            purchaseitemFacade.create(item);
        }
    }

    @Override
    public Collection<PurchaseItem> getItemsInBasket() {
        Collection<PurchaseItem> items = new ArrayList<PurchaseItem>();
        for (PurchaseitemEntity entity : basketItems) {
            PurchaseItem item = mapEntityToDto(entity);
            items.add(item);
        }
        return items;
    }

    private PurchaseItem mapEntityToDto(PurchaseitemEntity entity) {
        PurchaseItem dto = new PurchaseItem();
        ProductEntity product = productFacade.find(entity.getProductid());
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitprice());
        dto.setLineAmount(entity.getLineamount());
        return dto;
    }
}
