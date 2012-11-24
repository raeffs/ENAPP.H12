package ch.hslu.enapp.h12.tafleisc.control;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Payment;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.PaymentFailedException;
import ch.hslu.enapp.h12.tafleisc.external.postfinance.PaymentRequest;
import ch.hslu.enapp.h12.tafleisc.external.postfinance.PaymentRequestComposer;
import ch.hslu.enapp.h12.tafleisc.external.postfinance.Response;
import java.net.ProxySelector;
import java.net.URI;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
public class PostfinanceFacade {

    private static final String PAYMENT_URL = "https://e-payment.postfinance.ch/ncol/test/orderdirect.asp";
    private static final String OPERATION_CODE = "SAL";
    private static final String CURRENCY_CODE = "CHF";
    private static final String PURCHASE_ID_FORMAT = "TAFLEISC%s";
    @Inject
    private PaymentRequestComposer requestComposer;

    public int doPayment(Payment payment) throws PaymentFailedException {
        PaymentRequest paymentRequest = getPaymentRequest(payment);
        HttpPost postRequest = requestComposer.composeRequest(paymentRequest);
        postRequest.setURI(URI.create(PAYMENT_URL));
        DefaultHttpClient client = new DefaultHttpClient();
        client.setRoutePlanner(new ProxySelectorRoutePlanner(
                client.getConnectionManager().getSchemeRegistry(),
                ProxySelector.getDefault()));
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(postRequest);
        } catch (Exception e) {
            throw new PaymentFailedException(String.format("Payment request failed: %s", e.getMessage()));
        }
        checkHttpState(httpResponse);
        Response response = unmarshalResponse(httpResponse);
        checkResponse(response);
        return response.getPaymentId();
    }

    private PaymentRequest getPaymentRequest(Payment payment) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPurchaseId(String.format(PURCHASE_ID_FORMAT, payment.getPurchaseId()));
        paymentRequest.setAmount(String.valueOf(payment.getAmount() * 100));
        paymentRequest.setCardNumber(String.valueOf(payment.getCreditCardNumber()));
        paymentRequest.setCardVerificationCode(String.valueOf(payment.getCreditCardVerificationCode()));
        paymentRequest.setCardExpiryDate(
                String.format("%02d", payment.getCreditCardExpiryMonth()) + String.format("%02d", payment.getCreditCardExpiryYear()));
        paymentRequest.setCurrency(CURRENCY_CODE);
        paymentRequest.setOperationCode(OPERATION_CODE);
        return paymentRequest;
    }

    private void checkResponse(Response response) throws PaymentFailedException {
        if (response.getErrorState() != 0) {
            throw new PaymentFailedException(String.format("Postfinance service reported an error: %s", response.getErrorMessage()));
        }
    }

    private void checkHttpState(HttpResponse httpResponse) throws PaymentFailedException {
        int state = httpResponse.getStatusLine().getStatusCode();
        if (state < 200 || state >= 300) {
            throw new PaymentFailedException(String.format("Payment request failed: %s", httpResponse.getStatusLine().getReasonPhrase()));
        }
    }

    private Response unmarshalResponse(HttpResponse httpResponse) throws PaymentFailedException {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(Response.class).createUnmarshaller();
            return (Response) unmarshaller.unmarshal(httpResponse.getEntity().getContent());
        } catch (Exception e) {
            throw new PaymentFailedException(String.format("Could not unmarshal response from postfinance service: %s", e.getMessage()));
        }
    }
}
