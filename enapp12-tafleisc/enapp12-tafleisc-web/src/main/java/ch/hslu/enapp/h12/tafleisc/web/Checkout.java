package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.ICustomerService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Payment;
import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Named(value = "checkout")
@RequestScoped
public class Checkout implements Serializable {

    @Inject
    private Basket basket;
    @Inject
    private ICustomerService customerService;
    
    private String creditCardNumber = "5399-9999-9999-9999";
    private String creditCardVerificationCode = "123";
    private String creditCardExpiryDate = "12/15";

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardVerificationCode() {
        return creditCardVerificationCode;
    }

    public void setCreditCardVerificationCode(String creditCardVerificationCode) {
        this.creditCardVerificationCode = creditCardVerificationCode;
    }

    public String getCreditCardExpiryDate() {
        return creditCardExpiryDate;
    }

    public void setCreditCardExpiryDate(String creditCardExpiryDate) {
        this.creditCardExpiryDate = creditCardExpiryDate;
    }
    
    public void checkoutButtenClicked(ActionEvent event) throws Exception {
        checkout();
        FacesContext.getCurrentInstance().getExternalContext().redirect("./confirm.xhtml");
    }
    
    private void checkout() throws Exception {
        Payment payment = new Payment();
        payment.setCustomerId(getCustomerId());
        payment.setCreditCardNumber(parseCreditCardNumber());
        payment.setCreditCardVerificationCode(parseCreditCardVerificationCode());
        payment.setCreditCardExpiryYear(parseCreditCardExpiryYear());
        payment.setCreditCardExpiryMonth(parseCreditCardExpiryMonth());
        basket.getPurchaseService().checkout(payment);
    }
    
    private long parseCreditCardNumber() {
        String content = getCreditCardNumber();
        content = content.replace(" ", "");
        content = content.replace("-", "");
        return Long.parseLong(content);
    }
    
    private int parseCreditCardVerificationCode() {
        String content = getCreditCardVerificationCode();
        return Integer.parseInt(content);
    }
    
    private int parseCreditCardExpiryYear() {
        String content = getCreditCardExpiryDate();
        content = content.replace("/", "");
        content = content.substring(2,4);
        return Integer.parseInt(content);
    }
    
    private int parseCreditCardExpiryMonth() {
        String content = getCreditCardExpiryDate();
        content = content.replace("/", "");
        content = content.substring(0,2);
        return Integer.parseInt(content);
    }
    
    private int getCustomerId() {
        Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return customerService.getCustomerId(user.getName());
    }
}
