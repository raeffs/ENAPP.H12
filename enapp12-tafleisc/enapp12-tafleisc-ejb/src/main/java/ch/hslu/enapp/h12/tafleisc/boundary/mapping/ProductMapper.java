package ch.hslu.enapp.h12.tafleisc.boundary.mapping;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.external.dynnav.Item;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class ProductMapper {

    public Product mapEntityToDto(Item source) {
        Product destination = new Product();
        destination.setProductId(source.getNo());
        destination.setName(source.getDescription());
        destination.setDescription(String.format("by %s (%s)", source.getOwner(), source.getKind()));
        destination.setUnitPrice(source.getUnitPrice().longValue());
        return destination;
    }
}
