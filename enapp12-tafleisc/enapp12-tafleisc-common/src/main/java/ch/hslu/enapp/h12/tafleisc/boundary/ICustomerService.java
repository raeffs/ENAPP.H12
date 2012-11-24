package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Customer;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.CustomerInfo;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Local
public interface ICustomerService {

    int getCustomerId(String username);

    Collection<CustomerInfo> getCustomers();

    Customer getCustomer(int customerId);

    void updateCustomer(Customer customer);
}
