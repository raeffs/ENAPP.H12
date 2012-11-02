package ch.hslu.enapp.h12.tafleisc.boundary.mapping;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.Item;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class ProductMapper {

    public Product mapEntityToDto(ProductEntity entity) {
        Product dto = new Product();
        dto.setProductId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUnitPrice(entity.getUnitprice());
        return dto;
    }
    
    public Product mapErpEntityToDto(Item entity) {
        Product dto = new Product();
        dto.setProductId(Integer.parseInt(entity.getNo().substring(3)));
        dto.setName(entity.getDescription());
        dto.setDescription(String.format("by %s (%s)", entity.getOwner(), entity.getKind()));
        dto.setUnitPrice(entity.getUnitPrice().longValue());
        return dto;
    }
}
