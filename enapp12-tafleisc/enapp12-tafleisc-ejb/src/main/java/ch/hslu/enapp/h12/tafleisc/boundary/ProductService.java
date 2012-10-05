package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.ProductMapper;
import ch.hslu.enapp.h12.tafleisc.control.ProductFacade;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
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
    private ProductFacade productFacade;
    
    @Inject
    private ProductMapper productMapper;

    public ProductService() {}
    
    public ProductService(ProductFacade productFacade, ProductMapper productMapper) {
        this.productFacade = productFacade;
        this.productMapper = productMapper;
    }

    @Override
    public Collection<Product> getProducts() {
        Collection<Product> products = new ArrayList<Product>();
        for (ProductEntity entity : productFacade.findAll()) {
            products.add(productMapper.mapEntityToDto(entity));
        }
        return products;
    }
}
