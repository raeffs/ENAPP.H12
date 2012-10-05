package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
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

    public ProductService() {}
    
    public ProductService(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Override
    public Collection<Product> getProducts() {
        Collection<Product> products = new ArrayList<Product>();
        for (ProductEntity entity : productFacade.findAll()) {
            products.add(mapEntityToDto(entity));
        }
        return products;
    }
    
    private Product mapEntityToDto(ProductEntity entity) {
        Product dto = new Product();
        dto.setProductId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUnitPrice(entity.getUnitprice());
        return dto;
    }
}
