package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Customer;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.CustomerInfo;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.CustomerMapper;
import ch.hslu.enapp.h12.tafleisc.control.CustomerFacade;
import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import java.util.ArrayList;
import java.util.Collection;
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
    @Inject
    private CustomerMapper customerMapper;

    @Override
    public int getCustomerId(String username) {
        CustomerEntity customer = customerFacade.findByUsername(username);
        if (customer != null) {
            return customer.getId();
        }
        return 0;
    }

    @Override
    public Collection<CustomerInfo> getCustomers() {
        Collection<CustomerInfo> customers = new ArrayList<CustomerInfo>();
        for (CustomerEntity entity : customerFacade.findAll()) {
            customers.add(customerMapper.mapEntityToCustomerInfo(entity));
        }
        return customers;
    }

    @Override
    public Customer getCustomer(int customerId) {
        return customerMapper.mapEntityToDto(customerFacade.findById(customerId));
    }

    @Override
    public void updateCustomer(Customer customer) {
        CustomerEntity entity = customerFacade.findById(customer.getCustomerId());
        customerMapper.mapDtoToEntity(customer, entity);
        customerFacade.edit(entity);
    }

    @Override
    public void createCustomer(String username, String password, String name, String address, String email) {
        CustomerEntity entity = new CustomerEntity(0);
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setName(name);
        entity.setAddress(address);
        entity.setEmail(email);
        entity.setGroupid(1);
        customerFacade.create(entity);
    }
}
