package ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance;

import java.util.SortedMap;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
@LocalBean
public class PaymentRequestComposer extends PostRequestComposer<PaymentRequest> {
    
    @Override
    protected void addParameters(SortedMap<String, String> parameters, PaymentRequest payload) {
        parameters.put(ORDER_ID_PARAMETER, payload.getOrderId());
        parameters.put(AMOUNT_PARAMETER, payload.getAmount());
        parameters.put(CURRENCY_PARAMETER, payload.getCurrency());
        parameters.put(CARD_NUMBER_PARAMETER, payload.getCardNumber());
        parameters.put(CARD_EXPIRY_DATE_PARAMETER, payload.getCardExpiryDate());
        parameters.put(CARD_VERIFICATION_CODE_PARAMETER, payload.getCardVerificationCode());
        parameters.put(OPERATION_CODE_PARAMETER, payload.getOperationCode());
    }

}
