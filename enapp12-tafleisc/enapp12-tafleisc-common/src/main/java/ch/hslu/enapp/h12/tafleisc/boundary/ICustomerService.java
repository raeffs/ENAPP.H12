package ch.hslu.enapp.h12.tafleisc.boundary;

import javax.ejb.Local;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Local
public interface ICustomerService {

    int getCustomerId(String username);
}
