package ch.hslu.enapp.h12.tafleisc.external.postfinance;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class PaymentRequest {

    private String purchaseId;
    private String amount;
    private String currency;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardVerificationCode;
    private String operationCode;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String orderId) {
        this.purchaseId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardVerificationCode() {
        return cardVerificationCode;
    }

    public void setCardVerificationCode(String cardVerificationCode) {
        this.cardVerificationCode = cardVerificationCode;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }
}
