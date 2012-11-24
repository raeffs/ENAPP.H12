package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseStatus;
import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.SalesOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PurchaseProcessor {

    private static final int REFRESH_INTERVAL_IN_MS = 10000;
    @Resource
    private TimerService timerService;
    @Inject
    private EnappDeamonFacade enappDeamonFacade;
    @Inject
    private PurchaseFacade purchaseFacade;
    @Inject
    private CustomerFacade customerFacade;

    public void beginPurchaseProcessing(int purchaseId) {
        scheduleProcessing(purchaseId);
    }

    private void scheduleProcessing(int purchaseId) {
        timerService.createTimer(REFRESH_INTERVAL_IN_MS, purchaseId);
    }

    @Timeout
    public void process(Timer timer) {
        int purchaseId = Integer.parseInt(timer.getInfo().toString());
        PurchaseEntity purchase = purchaseFacade.findById(purchaseId);
        processPurchase(purchase);
    }

    private void processPurchase(PurchaseEntity purchase) {
        if (purchase.getCorrelationid() == null || purchase.getCorrelationid().length() == 0) {
            sendPurchaseToErpSystem(purchase);
        } else {
            checkPurchaseStatus(purchase);
        }
    }

    private void sendPurchaseToErpSystem(PurchaseEntity purchase) {
        String correlationId = enappDeamonFacade.sendPurchase(purchase);
        purchase.setCorrelationid(correlationId);
        purchase.setStatus(PurchaseStatus.Submitted.getIndex());
        purchaseFacade.edit(purchase);
        scheduleProcessing(purchase.getId());
    }

    public void checkPurchaseStatus(PurchaseEntity purchase) {
        SalesOrder order = null;
        try {
            order = enappDeamonFacade.getOrderState(purchase.getCorrelationid());
        } catch (Exception e) {
            Logger.getLogger(PurchaseProcessor.class.getName()).log(Level.INFO, e.getMessage());
        }
        if (order == null || order.hasFailed()) {
            purchase.setStatus(PurchaseStatus.Failed.getIndex());
        } else if (order.isProcessing()) {
            scheduleProcessing(purchase.getId());
        } else if (order.isOk()) {
            if (order.wasCustomerCreated()) {
                assignExternalCustomerId(purchase.getCustomerid(), order.getExternalCustomerId());
            }
            purchase.setStatus(PurchaseStatus.Acepted.getIndex());
        } else {
            purchase.setStatus(PurchaseStatus.Failed.getIndex());
        }
        purchaseFacade.edit(purchase);
    }

    private void assignExternalCustomerId(int customerId, String externalId) {
        CustomerEntity customer = customerFacade.findById(customerId);
        customer.setDynnavid(externalId);
        customerFacade.edit(customer);
    }
}
