package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Purchase;
import ch.hslu.enapp.h12.tafleisc.external.postfinance.PaymentRequest;
import ch.hslu.enapp.h12.tafleisc.external.postfinance.PaymentRequestComposer;
import ch.hslu.enapp.h12.tafleisc.external.postfinance.Response;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final String OPERATION_CODE = "SAL";
    
    @Inject
    private PaymentRequestComposer requestComposer;

    public String doPayment(Purchase purchase) {
        PaymentRequest paymentRequest = getPaymentRequest(purchase);
        HttpPost postRequest = requestComposer.composeRequest(paymentRequest);
        postRequest.setURI(URI.create(PAYMENT_URL));
        
        String paymentId = null;
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = client.execute(postRequest);
            checkHttpState(httpResponse);
            Response response = unmarshalResponse(httpResponse);
            checkResponse(response);
            paymentId = response.getPaymentId();
        } catch (Exception e) {
            Logger.getLogger(PostfinanceFacade.class.getName()).log(Level.INFO, e.getMessage());
        }
        return paymentId;
    }
    
    private PaymentRequest getPaymentRequest(Purchase purchase) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPurchaseId(purchase.getPurchaseId());
        paymentRequest.setAmount(String.valueOf(purchase.getAmount()));
        paymentRequest.setCardNumber(String.valueOf(purchase.getCreditCardNumber()));
        paymentRequest.setCardVerificationCode(String.valueOf(purchase.getCreditCardVerificationCode()));
        paymentRequest.setCardExpiryDate(
                String.format("%02d", purchase.getCreditCardExpiryMonth()) + String.format("%02d", purchase.getCreditCardExpiryYear()));
        paymentRequest.setCurrency("CHF");
        paymentRequest.setOperationCode(OPERATION_CODE);
        return paymentRequest;
    }
    
    private void checkResponse(Response response) throws Exception {
        if (Integer.parseInt(response.getErrorState()) != 0) {
            throw new Exception(String.format("Payment failed: %s", response.getErrorMessage()));
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
