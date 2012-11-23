package ch.hslu.enapp.h12.tafleisc.boundary.mapping;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseItemEntity;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PurchaseItemMapper {
    
    public PurchaseItemEntity mapDtoToEntity(PurchaseItem source) {
        PurchaseItemEntity destination = new PurchaseItemEntity(0);
        destination.setProductid(source.getProductId());
        destination.setUnitprice(source.getUnitPrice());
        destination.setQuantity(source.getQuantity());
        destination.setLineamount(source.getLineAmount());
        return destination;
    }
    
}
