package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.PurchaseItemEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseItemEntity_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PurchaseItemFacade extends AbstractFacade<PurchaseItemEntity> {

    @PersistenceContext(unitName = "ch.hslu.enapp.h12_enapp12-tafleisc-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseItemFacade() {
        super(PurchaseItemEntity.class);
    }
    
    public List<PurchaseItemEntity> findByPurchaseId(int purchaseId) {
        return findMultipleWhere(PurchaseItemEntity_.purchaseid, purchaseId);
    }
}
