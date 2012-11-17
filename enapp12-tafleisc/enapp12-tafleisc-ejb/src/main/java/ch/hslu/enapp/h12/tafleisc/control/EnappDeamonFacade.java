package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Purchase;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.Customer;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.Line;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.PurchaseMessage;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class EnappDeamonFacade {
    
    @Resource(mappedName = "jms/EnappQueueFactory")
    private QueueConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/enappqueue")
    private Queue queue;
    
    public void sendPurchase(Purchase purchase, Collection<PurchaseItem> items) {
        PurchaseMessage message = getPurchaseMessage(purchase, items);
        String content = marshalMessage(message);
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage jmsMessage = session.createTextMessage(content);
            jmsMessage.setStringProperty("MessageFormat", "Version 1.5");
            long correlationId =  (new Random().nextInt(8999) +  1000) 
                * 10000000000000l
                + Calendar.getInstance().getTimeInMillis();
            jmsMessage.setJMSCorrelationID(String.valueOf(correlationId));
            
            MessageProducer producer = session.createProducer(queue);
            producer.send(jmsMessage);

        } catch (Exception e) {
            Logger.getLogger(EnappDeamonFacade.class.getName()).log(Level.INFO, "message");
        }
    }
    
    private String marshalMessage(PurchaseMessage message) {
        String textMessage = null;
        try {
            JAXBContext context = JAXBContext.newInstance(PurchaseMessage.class);
            StringWriter writer = new StringWriter();  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.marshal(message, writer);
            textMessage = writer.toString();
        } catch (Exception e) {
            Logger.getLogger(EnappDeamonFacade.class.getName()).log(Level.INFO, e.getMessage());
        }
        return textMessage;
    }

    private PurchaseMessage getPurchaseMessage(Purchase purchase, Collection<PurchaseItem> items) {
        PurchaseMessage message = new PurchaseMessage();
        message.setPurchaseId(purchase.getPurchaseId());
        message.setPaymentId(purchase.getPaymentId());
        message.setStudent("tafleisc");
        message.setTotalAmount(purchase.getAmount());
        message.setDate(new Date());
        
        Customer customer = new Customer();
        customer.setExternalCustomerId("");
        customer.setFullName("Raphael Fleischlin");
        customer.setAddress("Musterstrasse 11");
        customer.setPostCode("6000");
        customer.setCity("Luzern");
        customer.setUsername("raeffs");
        message.setCustomer(customer);
        
        Collection<Line> lines = new ArrayList<Line>();
        for (PurchaseItem item : items) {
            Line line = new Line();
            line.setProductId(item.getProductId());
            line.setDescription(item.getProductName());
            line.setQuantity(item.getQuantity());
            line.setAmount(item.getLineAmount());
            lines.add(line);
        }
        message.setLines(lines);
        
        return message;
    }
    
}
