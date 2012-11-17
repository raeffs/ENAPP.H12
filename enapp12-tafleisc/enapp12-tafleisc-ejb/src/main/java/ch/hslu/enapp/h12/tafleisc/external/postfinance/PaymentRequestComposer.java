package ch.hslu.enapp.h12.tafleisc.external.postfinance;

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
        parameters.put(OPERATION_CODE_PARAMETER, payload.getOperationCode());
        parameters.put(CURRENCY_PARAMETER, payload.getCurrency());
        parameters.put(PURCHASE_ID_PARAMETER, payload.getPurchaseId());
        parameters.put(AMOUNT_PARAMETER, payload.getAmount());
        parameters.put(CARD_NUMBER_PARAMETER, payload.getCardNumber());
        parameters.put(CARD_EXPIRY_DATE_PARAMETER, payload.getCardExpiryDate());
        parameters.put(CARD_VERIFICATION_CODE_PARAMETER, payload.getCardVerificationCode());
    }

}
