package ch.hslu.enapp.h12.tafleisc.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
@LocalBean
public class MessageDigestHelper {
    
    public String computeSha1Hash(String plaintext) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            hash = convertToHexString(md.digest(plaintext.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            // should not happen :)
        }
        return hash;
    }
    
    private String convertToHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    
}
