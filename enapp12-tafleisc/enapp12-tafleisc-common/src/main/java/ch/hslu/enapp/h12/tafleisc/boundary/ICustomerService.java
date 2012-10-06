package ch.hslu.enapp.h12.tafleisc.boundary;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public interface ICustomerService {

    void login(String username, String password);

    void logout();
}
