package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.ICustomerService;
import ch.hslu.enapp.h12.tafleisc.boundary.IPurchaseService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Payment;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Named(value = "basket")
@SessionScoped
public class Basket implements Serializable {

    @Inject
    private IPurchaseService purchaseService;
    @Inject
    private ICustomerService customerService;

    public IPurchaseService getPurchaseService() {
        return purchaseService;
    }

    public Collection<PurchaseItem> getItems() {
        return purchaseService.getItemsInBasket();
    }

    public void checkout() throws Exception {
        Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        int customerId = customerService.getCustomerId(user.getName());
        
        Payment payment = new Payment();
        payment.setCustomerId(customerId);
        payment.setCreditCardNumber(5399999999999999L);
        payment.setCreditCardVerificationCode(123);
        payment.setCreditCardExpiryYear(20);
        payment.setCreditCardExpiryMonth(8);
        purchaseService.checkout(payment);
        
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("./checkout.xhtml");
    }

    public void checkoutButtenClicked(ActionEvent event)
            throws Exception {
        checkout();
    }
}
