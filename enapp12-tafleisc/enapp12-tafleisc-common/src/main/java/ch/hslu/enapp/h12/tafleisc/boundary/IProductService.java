package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Local
public interface IProductService {

    Collection<Product> getProducts();
}
