package ch.hslu.enapp.h12.tafleisc.boundary.mapping;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Customer;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.CustomerInfo;
import ch.hslu.enapp.h12.tafleisc.control.GroupFacade;
import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import ch.hslu.enapp.h12.tafleisc.entity.GroupEntity;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class CustomerMapper {

    @Inject
    private GroupFacade groupFacade;

    public CustomerInfo mapEntityToCustomerInfo(CustomerEntity source) {
        CustomerInfo destination = new CustomerInfo();
        destination.setCustomerId(source.getId());
        destination.setUsername(source.getUsername());
        return destination;
    }

    private String getGroupName(CustomerEntity customer) {
        GroupEntity group = groupFacade.findById(customer.getGroupid());
        return group.getName();
    }

    public Customer mapEntityToDto(CustomerEntity source) {
        Customer destination = new Customer();
        destination.setCustomerId(source.getId());
        destination.setUsername(source.getUsername());
        destination.setName(source.getName());
        destination.setAddress(source.getAddress());
        destination.setEmail(source.getEmail());
        destination.setDynnavid(source.getDynnavid());
        destination.setGroup(getGroupName(source));
        return destination;
    }

    public void mapDtoToEntity(Customer source, CustomerEntity destination) {
        destination.setName(source.getName());
        destination.setAddress(source.getAddress());
        destination.setDynnavid(source.getDynnavid());
    }
}
