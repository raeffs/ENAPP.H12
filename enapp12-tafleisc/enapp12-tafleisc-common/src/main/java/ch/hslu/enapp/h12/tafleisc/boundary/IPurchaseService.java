package ch.hslu.enapp.h12.tafleisc.boundary;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public interface IPurchaseService {
    
    void addProductToBasket(int productId, int amount);
    
    void checkout();
    
}
