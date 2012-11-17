package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.ProductMapper;
import ch.hslu.enapp.h12.tafleisc.control.DynNavFacade;
import ch.hslu.enapp.h12.tafleisc.external.dynnav.Item;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class ProductService implements IProductService {

    @Inject
    private DynNavFacade productFacade;
    
    @Inject
    private ProductMapper productMapper;
    
    @Override
    public Collection<Product> getProducts() {
        Collection<Product> products = new ArrayList<Product>();
        for (Item item : productFacade.findAll()) {
            products.add(productMapper.mapEntityToDto(item));
        }
        return products;
    }

}
