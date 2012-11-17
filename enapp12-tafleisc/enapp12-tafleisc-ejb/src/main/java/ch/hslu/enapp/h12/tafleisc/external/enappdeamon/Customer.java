package ch.hslu.enapp.h12.tafleisc.external.enappdeamon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    
    @XmlElement(name="dynNavCustNo")
    private String externalCustomerId;
    @XmlElement(name="name")
    private String fullName;
    @XmlElement(name="address")
    private String address;
    @XmlElement(name="postCode")
    private String postCode;
    @XmlElement(name="city")
    private String city;
    @XmlElement(name="shopLoginname")
    private String username;

    public String getExternalCustomerId() {
        return externalCustomerId;
    }

    public void setExternalCustomerId(String externalCustomerId) {
        this.externalCustomerId = externalCustomerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
