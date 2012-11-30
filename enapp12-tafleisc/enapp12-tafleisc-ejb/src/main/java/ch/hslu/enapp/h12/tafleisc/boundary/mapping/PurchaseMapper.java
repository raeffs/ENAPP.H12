package ch.hslu.enapp.h12.tafleisc.boundary.mapping;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Purchase;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseStatus;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PurchaseMapper {

    public Purchase mapEntityToDto(PurchaseEntity source) {
        Purchase destination = new Purchase();
        destination.setPurchaseId(source.getId());
        destination.setCustomerId(source.getCustomerid());
        destination.setDatetime(source.getDatetime());
        destination.setTotalAmount(source.getTotalamount());
        destination.setPaymentId(source.getPaymentid());
        destination.setCorrelationId(source.getCorrelationid());
        destination.setStatus(PurchaseStatus.fromIndex(source.getStatus()));
        return destination;
    }
}
