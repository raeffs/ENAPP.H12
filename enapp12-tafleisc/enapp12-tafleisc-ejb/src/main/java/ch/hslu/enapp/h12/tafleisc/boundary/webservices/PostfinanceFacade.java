package ch.hslu.enapp.h12.tafleisc.boundary.webservices;

import ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.PaymentRequest;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.PaymentRequestComposer;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.Response;
import java.io.IOException;
import java.net.URI;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PostfinanceFacade {
    
    private static final String PAYMENT_URL = "https://e-payment.postfinance.ch/ncol/test/orderdirect.asp";
    
    @Inject
    private PaymentRequestComposer requestComposer;

    public void doPayment(PaymentRequest payment) {
        HttpPost request = requestComposer.composeRequest(payment);
        request.setURI(URI.create(PAYMENT_URL));
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = client.execute(request);
            checkHttpState(httpResponse);
            Response response = unmarshalResponse(httpResponse);
            // TODO: do something
        } catch (Exception e) {
            // TODO
        }
    }
    
    private void checkHttpState(HttpResponse httpResponse) throws Exception {
        int state = httpResponse.getStatusLine().getStatusCode();
        if (state < 200 || state >= 300) {
            throw new Exception(httpResponse.getStatusLine().getReasonPhrase());
        }
    }
    
    private Response unmarshalResponse(HttpResponse httpResponse) throws JAXBException, IOException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(Response.class).createUnmarshaller();
        return (Response) unmarshaller.unmarshal(httpResponse.getEntity().getContent());
    }

}
