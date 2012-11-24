package ch.hslu.enapp.h12.tafleisc.admin;

import ch.hslu.enapp.h12.tafleisc.boundary.ICustomerService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Customer;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.CustomerInfo;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@ManagedBean(name = "customerController")
@ViewScoped
public class CustomerController {

    @Inject
    private ICustomerService customerService;
    private Collection<CustomerInfo> list;
    private Customer currentItem;
    private boolean isInEditMode;

    @PostConstruct
    public void init() {
        currentItem = new Customer();
        list = customerService.getCustomers();
    }

    public boolean isInEditMode() {
        return isInEditMode;
    }

    public Customer getCurrentItem() {
        return currentItem;
    }

    public Collection<CustomerInfo> list() {
        return list;
    }

    public void edit(CustomerInfo customer) {
        currentItem = customerService.getCustomer(customer.getCustomerId());
        isInEditMode = true;
    }

    public void save() {
        customerService.updateCustomer(currentItem);
        currentItem = new Customer();
        isInEditMode = false;
    }

    public void discard() {
        currentItem = new Customer();
        isInEditMode = false;
    }
}
