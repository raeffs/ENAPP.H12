package ch.hslu.enapp.h12.tafleisc.boundary.webservices;

import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.Item;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemFields;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemFilter;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemPort;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemService;
import ch.hslu.enapp.h12.tafleisc.helper.AuthenticationHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
@LocalBean
public class DynNavFacade {
    
    private static final String AUTHENTICATION_DOMAIN = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.domain";
    private static final String AUTHENTICATION_USER = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.user";
    private static final String AUTHENTICATION_PASSWORD = "ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.password";
    
    private ItemPort servicePort;
    
    private Collection<Item> items;
    
    public DynNavFacade() {
        ItemService service = new ItemService();
        servicePort = service.getItemPort();
    }
    
    public Collection<Item> getItems() {
        if (items == null) {
            items = getItemsFromService();
        }
        return items;
    }
    
    private Collection<Item> getItemsFromService() {
        setServiceAuthenticator();
        return servicePort.readMultiple(getFilters(), null, 0).getItem();
    }
    
    private void setServiceAuthenticator() {
        AuthenticationHelper.setAuthenticator(
                System.getProperty(AUTHENTICATION_DOMAIN),
                System.getProperty(AUTHENTICATION_USER),
                System.getProperty(AUTHENTICATION_PASSWORD));
    }
    
    private List<ItemFilter> getFilters() {
        List<ItemFilter> list = new ArrayList<ItemFilter>();
        ItemFilter filter = new ItemFilter();
        filter.setField(ItemFields.PRODUCT_GROUP_CODE);
        filter.setCriteria("MP3");
        list.add(filter);
        return list;
    }
    
}
