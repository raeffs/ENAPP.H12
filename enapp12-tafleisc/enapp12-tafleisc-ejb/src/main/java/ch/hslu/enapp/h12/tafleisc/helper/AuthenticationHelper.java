package ch.hslu.enapp.h12.tafleisc.helper;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class AuthenticationHelper {

    public static void setAuthenticator(final String domain, final String user, final String password) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(domain + "\\" + user, password.toCharArray());
            }
        });
    }
}
