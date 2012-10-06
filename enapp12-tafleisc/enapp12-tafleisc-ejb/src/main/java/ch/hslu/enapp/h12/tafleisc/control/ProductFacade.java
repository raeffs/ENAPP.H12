package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class ProductFacade extends AbstractFacade<ProductEntity> {

    @PersistenceContext(unitName = "ch.hslu.enapp.h12_enapp12-tafleisc-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(ProductEntity.class);
    }

    public ProductEntity find(int productId) {
        return findSingleWhere(ProductEntity_.id, productId);
    }

    public boolean exists(int productId) {
        return (countWhere(ProductEntity_.id, productId) == 1);
    }
}
