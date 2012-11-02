package ch.hslu.enapp.h12.tafleisc.helper;

import java.util.Properties;
import javax.ejb.Singleton;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Singleton
public class ConfigurationHelper {
    
    private String dynNavDomain;
    private String dynNavUser;
    private String dynNavPassword;
    
    public ConfigurationHelper() {
        dynNavDomain = System.getProperty("ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.domain");
        dynNavUser = System.getProperty("ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.user");
        dynNavPassword = System.getProperty("ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.password");
        Properties prop = System.getProperties();
    }

    public String getDynNavDomain() {
        return dynNavDomain;
    }

    public String getDynNavUser() {
        return dynNavUser;
    }

    public String getDynNavPassword() {
        return dynNavPassword;
    }
    
}
