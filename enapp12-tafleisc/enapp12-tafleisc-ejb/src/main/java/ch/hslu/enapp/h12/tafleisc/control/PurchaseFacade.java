package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity_;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PurchaseFacade extends AbstractFacade<PurchaseEntity> {
    
    @Resource(mappedName = "jms/LocalQueueFactory")
    private QueueConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/localqueue")
    private Queue queue;

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
    
    public void processPurchase(int purchaseId) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MapMessage message = session.createMapMessage();
            message.setInt("PurchaseID", purchaseId);
            MessageProducer producer = session.createProducer(queue);
            producer.send(message);
        } catch (Exception ex) {
            Logger.getLogger(PurchaseFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
