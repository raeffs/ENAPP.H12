package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.GroupEntity;
import ch.hslu.enapp.h12.tafleisc.entity.GroupEntity_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class GroupFacade extends AbstractFacade<GroupEntity> {

    @PersistenceContext(unitName = "ch.hslu.enapp.h12_enapp12-tafleisc-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupFacade() {
        super(GroupEntity.class);
    }

    public GroupEntity findById(int groupId) {
        return findSingleWhere(GroupEntity_.id, groupId);
    }
}
