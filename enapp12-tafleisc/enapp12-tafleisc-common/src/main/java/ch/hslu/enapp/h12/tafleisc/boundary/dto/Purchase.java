package ch.hslu.enapp.h12.tafleisc.boundary.dto;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class Purchase {
    
    private String purchaseId;
    private long amount;
    private long creditCardNumber;
    private int creditCardVerificationCode;
    private int creditCardExpiryYear;
    private int creditCardExpiryMonth;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public int getCreditCardVerificationCode() {
        return creditCardVerificationCode;
    }

    public void setCreditCardVerificationCode(int creditCardVerificationCode) {
        this.creditCardVerificationCode = creditCardVerificationCode;
    }

    public int getCreditCardExpiryYear() {
        return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear(int creditCardExpiryYear) {
        this.creditCardExpiryYear = creditCardExpiryYear;
    }

    public int getCreditCardExpiryMonth() {
        return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
    }
    
}
