package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PurchaseFacade extends AbstractFacade<PurchaseEntity> {
    
    @PersistenceContext(unitName = "ch.hslu.enapp.h12_enapp12-tafleisc-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseFacade() {
        super(PurchaseEntity.class);
    }
    
    public PurchaseEntity findById(int purchaseId) {
        return findSingleWhere(PurchaseEntity_.id, purchaseId);
    }
}
