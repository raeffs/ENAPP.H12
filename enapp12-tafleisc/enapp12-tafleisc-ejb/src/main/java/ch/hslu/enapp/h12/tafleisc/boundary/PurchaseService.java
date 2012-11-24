package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Payment;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseStatus;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.PaymentFailedException;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.ProductMapper;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.PurchaseItemMapper;
import ch.hslu.enapp.h12.tafleisc.control.DynNavFacade;
import ch.hslu.enapp.h12.tafleisc.control.PostfinanceFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseItemFacade;
import ch.hslu.enapp.h12.tafleisc.control.PurchaseProcessor;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseItemEntity;
import ch.hslu.enapp.h12.tafleisc.external.dynnav.Item;
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
    private DynNavFacade productFacade;
    @Inject
    private PostfinanceFacade postfinanceFacade;
    @Inject
    private PurchaseFacade purchaseFacade;
    @Inject
    private PurchaseItemFacade purchaseItemFacade;
    @Inject
    private ProductMapper productMapper;
    @Inject
    private PurchaseItemMapper purchaseItemMapper;
    @Inject
    private PurchaseProcessor purchaseProcessor;
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
    public Collection<PurchaseItem> getItemsInBasket() {
        return basketItems;
    }

    @Override
    public Payment checkout(Payment payment) throws PaymentFailedException {
        PurchaseEntity purchase = createPurchase(payment.getCustomerId());
        saveBasketItems(purchase.getId());
        payment.setPurchaseId(purchase.getId());
        payment.setAmount(purchase.getTotalamount());
        int paymentId = postfinanceFacade.doPayment(payment);
        savePurchase(purchase, paymentId);
        purchaseProcessor.beginPurchaseProcessing(purchase.getId());
        basketItems.clear();
        return null;
    }

    private PurchaseEntity createPurchase(int customerId) {
        PurchaseEntity purchase = new PurchaseEntity(0);
        purchase.setCustomerid(customerId);
        purchase.setDatetime(new Date());
        purchase.setStatus(PurchaseStatus.Created.getIndex());
        purchase.setTotalamount(getTotalAmount());
        purchaseFacade.create(purchase);
        return purchase;
    }

    private void savePurchase(PurchaseEntity purchase, int paymentId) {
        purchase.setPaymentid(paymentId);
        purchase.setStatus(PurchaseStatus.Payed.getIndex());
        purchaseFacade.edit(purchase);
    }

    private long getTotalAmount() {
        long amount = 0;
        for (PurchaseItem item : basketItems) {
            amount += item.getLineAmount();
        }
        return amount;
    }

    private void saveBasketItems(int purchaseId) {
        for (PurchaseItem item : basketItems) {
            PurchaseItemEntity entity = purchaseItemMapper.mapDtoToEntity(item);
            entity.setPurchaseid(purchaseId);
            purchaseItemFacade.create(entity);
        }
    }
}
