package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseStatus;
import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.SalesOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;


/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@MessageDriven(mappedName = "jms/localqueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class PurchaseProcessor implements MessageListener {
    
    private static final int REFRESH_INTERVAL_IN_MS = 10000;
    
    @Resource
    private TimerService timerService;
    
    @Inject
    private EnappDeamonFacade enappDeamonFacade;
    @Inject
    private PurchaseFacade purchaseFacade;
    @Inject
    private CustomerFacade customerFacade;
    
    @Override
    public void onMessage(Message message) {
        try {
            int purchaseId = ((MapMessage)message).getInt("PurchaseID");
            PurchaseEntity purchase = purchaseFacade.findById(purchaseId);
            String correlationId = enappDeamonFacade.sendPurchase(purchase);
            purchase.setCorrelationid(correlationId);
            purchase.setStatus(PurchaseStatus.Submitted.getIndex());
            purchaseFacade.edit(purchase);
            schedulePurchaseCheck(purchaseId);
        } catch (Exception e) {
            Logger.getLogger(PurchaseProcessor.class.getName()).log(Level.INFO, e.getMessage());
        }
    }
    
    private void schedulePurchaseCheck(int purchaseId) {
        timerService.createTimer(REFRESH_INTERVAL_IN_MS, purchaseId);
    }
    
    @Timeout
    public void purchaseCheck(Timer timer) {
        int purchaseId = Integer.parseInt(timer.getInfo().toString());
        PurchaseEntity purchase = purchaseFacade.findById(purchaseId);
        SalesOrder order = null;
        try {
            order = enappDeamonFacade.getOrderState(purchase.getCorrelationid());
        } catch (Exception e) {
            Logger.getLogger(PurchaseProcessor.class.getName()).log(Level.INFO, e.getMessage());
        }
        if (order == null || order.hasFailed()) {
            purchase.setStatus(PurchaseStatus.Failed.getIndex());
        } else if (order.isProcessing()) {
            schedulePurchaseCheck(purchaseId);
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
