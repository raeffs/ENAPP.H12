package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.ProductMapper;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.DynNavFacade;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.Item;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
@Named("ErpProductService")
public class ErpProductService implements IProductService {

    @Inject
    private DynNavFacade serviceFacade;
    
    @Inject
    private ProductMapper productMapper;
    
    @Override
    public Collection<Product> getProducts() {
        Collection<Product> products = new ArrayList<Product>();
        for (Item item : serviceFacade.getItems()) {
            products.add(productMapper.mapErpEntityToDto(item));
        }
        return products;
    }

}
