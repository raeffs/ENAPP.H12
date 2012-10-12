package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.control.CustomerFacade;
import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class CustomerService implements ICustomerService {

    @Inject
    private CustomerFacade customerFacade;

    @Override
    public int getCustomerId(String username) {
        CustomerEntity customer = customerFacade.findByUsername(username);
        if (customer != null) {
            return customer.getId();
        }
        return 0;
    }
}
