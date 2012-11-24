package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.entity.CustomerEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseItemEntity;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.Customer;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.Line;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.PurchaseMessage;
import ch.hslu.enapp.h12.tafleisc.external.enappdeamon.SalesOrder;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class EnappDeamonFacade {
    
    private static final String STATUS_URL = "http://10.29.3.152/ENAPPDaemon-war/resources/salesorder/corr/%s";
    
    @Resource(mappedName = "jms/EnappQueueFactory")
    private QueueConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/enappqueue")
    private Queue queue;
    
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private PurchaseItemFacade purchaseItemFacade;
    
    public String sendPurchase(PurchaseEntity purchase) {
        String correlationId = getCorrelationId();
        String content = marshalMessage(getPurchaseMessage(purchase));
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage(content);
            message.setStringProperty("MessageFormat", "Version 1.5");
            message.setJMSCorrelationID(String.valueOf(correlationId));
            session.createProducer(queue).send(message);
        } catch (Exception e) {
            Logger.getLogger(EnappDeamonFacade.class.getName()).log(Level.INFO, e.getMessage());
        }
        return correlationId;
    }
    
    private String getCorrelationId() {
        return String.valueOf(
            (new Random().nextInt(8999) + 1000) * 10000000000000l
            + Calendar.getInstance().getTimeInMillis());
    }
    
    private PurchaseMessage getPurchaseMessage(PurchaseEntity purchase) {
        PurchaseMessage message = new PurchaseMessage();
        message.setPurchaseId(purchase.getId());
        message.setPaymentId(purchase.getPaymentid());
        message.setStudent("tafleisc");
        message.setTotalAmount(purchase.getTotalamount());
        message.setDate(purchase.getDatetime());
        message.setCustomer(getCustomer(purchase.getCustomerid()));
        message.setLines(getLines(purchase.getId()));
        return message;
    }
    
    private Customer getCustomer(int customerId) {
        CustomerEntity entity = customerFacade.findById(customerId);
        Customer customer = new Customer();
        customer.setExternalCustomerId(getDynNavId(entity.getDynnavid()));
        customer.setFullName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setPostCode("6000");
        customer.setCity("Luzern");
        customer.setUsername(entity.getUsername());
        return customer;
    }
    
    private String getDynNavId(String dynNavId) {
        if (dynNavId == null || dynNavId.length() == 0) {
            return "";
        } else {
            return dynNavId;
        }
    }
    
    private Collection<Line> getLines(int purchaseId) {
        Collection<Line> lines = new ArrayList<Line>();
        for (PurchaseItemEntity entity : purchaseItemFacade.findByPurchaseId(purchaseId)) {
            Line line = new Line();
            line.setProductId(entity.getProductid());
            line.setDescription("");
            line.setQuantity(entity.getQuantity());
            line.setAmount(entity.getLineamount());
            lines.add(line);
        }
        return lines;
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
    
    public SalesOrder getOrderState(String correlationId) throws Exception {
        HttpGet getRequest = new HttpGet(String.format(STATUS_URL, correlationId));
        HttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(getRequest);
        } catch (Exception e) {
            throw new Exception(String.format("Status request failed: %s", e.getMessage()));
        }
        return unmarshalResponse(httpResponse);
    }
    
    private SalesOrder unmarshalResponse(HttpResponse httpResponse) throws Exception {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(SalesOrder.class).createUnmarshaller();
            return (SalesOrder) unmarshaller.unmarshal(httpResponse.getEntity().getContent());
        } catch (Exception e) {
            throw new Exception(String.format("Could not unmarshal response from enapp deamon service: %s", e.getMessage()));
        }
    }

}
