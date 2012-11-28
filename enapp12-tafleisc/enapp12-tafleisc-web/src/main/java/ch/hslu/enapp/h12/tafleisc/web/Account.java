package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.ICustomerService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Customer;
import java.io.Serializable;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@ManagedBean(name = "account")
@ViewScoped
public class Account implements Serializable {
    
    @Inject
    private ICustomerService customerService;
    
    private int customerId;
    
    private String username;
    private String name;
    private String address;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @PostConstruct
    public void init() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        customerId = customerService.getCustomerId(principal.getName());
        reset();
    }
    
    public void reset() {
        Customer customer = customerService.getCustomer(customerId);
        setUsername(customer.getUsername());
        setName(customer.getName());
        setAddress(customer.getAddress());
        setEmail(customer.getEmail());
    }
    
    public void save() {
        Customer customer = customerService.getCustomer(customerId);
        customer.setName(getName());
        customer.setAddress(getAddress());
        customerService.updateCustomer(customer);
        reset();
    }
    
}
