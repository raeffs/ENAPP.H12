package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class CustomerFacade extends AbstractFacade<CustomerEntity> {

    @PersistenceContext(unitName = "ch.hslu.enapp.h12_enapp12-tafleisc-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(CustomerEntity.class);
    }
    
    public CustomerEntity findById(int customerId) {
        return findSingleWhere(CustomerEntity_.id, customerId);
    }

    public CustomerEntity findByUsername(String username) {
        return findSingleWhere(CustomerEntity_.username, username);
    }
}
