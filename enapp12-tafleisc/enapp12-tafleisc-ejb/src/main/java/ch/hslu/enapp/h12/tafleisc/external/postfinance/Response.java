package ch.hslu.enapp.h12.tafleisc.external.postfinance;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@XmlRootElement(name = "ncresponse")
public class Response {
    
    @XmlAttribute(name="orderID")
    private String purchaseId;
    @XmlAttribute(name="PAYID")
    private String paymentId;
    @XmlAttribute(name="NCSTATUS")
    private String errorState;
    @XmlAttribute(name="NCERROR")
    private String errorCode;
    @XmlAttribute(name="NCERRORPLUS")
    private String errorMessage;
    @XmlAttribute(name="ACCEPTANCE")
    private String acceptanceCode;
    @XmlAttribute(name="STATUS")
    private String transactionState;
    @XmlAttribute(name="ECI")
    private String eci;
    @XmlAttribute(name="amount")
    private String amount;
    @XmlAttribute(name="currency")
    private String currency;
    @XmlAttribute(name="PM")
    private String paymentMethod;
    @XmlAttribute(name="BRAND")
    private String paymentMethodBrand;
    /*
    @XmlAttribute(name="IPCTY")
    private String ipcty;
    @XmlAttribute(name="CCCTY")
    private String ccty;
    @XmlAttribute(name="CVCCheck")
    private String cvcCheck;
    @XmlAttribute(name="AAVCheck")
    private String aavCheck;
    @XmlAttribute(name="VC")
    private String vc;
    */

    public String getPurchaseId() {
        return purchaseId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getErrorState() {
        return errorState;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getAcceptanceCode() {
        return acceptanceCode;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public String getEci() {
        return eci;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentMethodBrand() {
        return paymentMethodBrand;
    }
    
}
