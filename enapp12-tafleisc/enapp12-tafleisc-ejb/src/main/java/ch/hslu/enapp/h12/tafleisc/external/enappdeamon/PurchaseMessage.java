package ch.hslu.enapp.h12.tafleisc.external.enappdeamon;

import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@XmlRootElement(name="purchaseMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseMessage {
    
    @XmlElement(name="payId")
    private String paymentId;
    @XmlElement(name="purchaseId")
    private String purchaseId;
    @XmlElement(name="student")
    private String student;
    @XmlElement(name="totalPrice")
    private long totalAmount;
    @XmlElement(name="date")
    private Date date;
    @XmlElement(name="customer")
    private Customer customer;
    @XmlElementWrapper(name="lines")
    @XmlElement(name="line")
    private Collection<Line> lines;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<Line> getLines() {
        return lines;
    }

    public void setLines(Collection<Line> lines) {
        this.lines = lines;
    }
    
}
