package ch.hslu.enapp.h12.tafleisc.external.postfinance;

import ch.hslu.enapp.h12.tafleisc.helper.MessageDigestHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.inject.Inject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public abstract class PostRequestComposer<T> {

    public static final String AFFILIATION_NAME = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.company";
    public static final String AUTHENTICATION_USER = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.user";
    public static final String AUTHENTICATION_PASSWORD = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.password";
    public static final String HASH_PASSPHRASE = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.postfinance.passphrase";
    public static final String AFFILIATION_NAME_PARAMETER = "PSPID";
    public static final String AUTHENTICATION_USER_PARAMETER = "USERID";
    public static final String AUTHENTICATION_PASSWORD_PARAMETER = "PSWD";
    public static final String HASHED_SIGNATURE_PARAMETER = "SHASIGN";
    public static final String PURCHASE_ID_PARAMETER = "ORDERID";
    public static final String AMOUNT_PARAMETER = "AMOUNT";
    public static final String CURRENCY_PARAMETER = "CURRENCY";
    public static final String CARD_NUMBER_PARAMETER = "CARDNO";
    public static final String CARD_EXPIRY_DATE_PARAMETER = "ED";
    public static final String CARD_VERIFICATION_CODE_PARAMETER = "CVC";
    public static final String OPERATION_CODE_PARAMETER = "OPERATION";
    @Inject
    private MessageDigestHelper messageDigestHelper;

    public HttpPost composeRequest(T payload) {
        HttpPost postRequest = new HttpPost();
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        addParameters(parameters, payload);
        addConfigurationParameters(parameters);
        addSignatureParameter(parameters);
        Collection<NameValuePair> nameValuePairs = getParametersAsNameValuePairs(parameters);
        postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        return postRequest;
    }

    protected abstract void addParameters(SortedMap<String, String> parameters, T payload);

    private void addConfigurationParameters(SortedMap<String, String> parameters) {
        parameters.put(AFFILIATION_NAME_PARAMETER, System.getProperty(AFFILIATION_NAME));
        parameters.put(AUTHENTICATION_USER_PARAMETER, System.getProperty(AUTHENTICATION_USER));
        parameters.put(AUTHENTICATION_PASSWORD_PARAMETER, System.getProperty(AUTHENTICATION_PASSWORD));
    }

    private void addSignatureParameter(SortedMap<String, String> parameters) {
        String signature = getSignature(parameters);
        String hashedSignature = messageDigestHelper.computeSha1Hash(signature);
        parameters.put(HASHED_SIGNATURE_PARAMETER, hashedSignature);
    }

    private String getSignature(SortedMap<String, String> parameters) {
        String signature = "";
        String hashPassphrase = System.getProperty(HASH_PASSPHRASE);
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            signature += String.format("%s=%s%s", parameter.getKey(), parameter.getValue(), hashPassphrase);
        }
        return signature;
    }

    private Collection<NameValuePair> getParametersAsNameValuePairs(Map<String, String> parameters) {
        Collection<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
        }
        return nameValuePairs;
    }
}
