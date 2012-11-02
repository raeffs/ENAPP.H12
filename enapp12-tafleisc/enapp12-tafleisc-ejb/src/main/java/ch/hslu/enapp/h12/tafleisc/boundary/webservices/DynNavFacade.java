package ch.hslu.enapp.h12.tafleisc.boundary.webservices;

import ch.hslu.enapp.h12.tafleisc.helper.AuthenticationHelper;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.Item;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemFields;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemFilter;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemList;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemPort;
import ch.hslu.enapp.h12.tafleisc.boundary.webservices.dynnav.ItemService;
import ch.hslu.enapp.h12.tafleisc.helper.ConfigurationHelper;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
@LocalBean
public class DynNavFacade {
    
    @Inject
    private ConfigurationHelper configuration;

    private ItemPort servicePort;
    
    public DynNavFacade() {
        ItemService service = new ItemService();
        servicePort = service.getItemPort();
    }
    
    public Collection<Item> getItems() {
        AuthenticationHelper.setAuthenticator(
                configuration.getDynNavDomain(),
                configuration.getDynNavUser(),
                configuration.getDynNavPassword());
        ItemList items = servicePort.readMultiple(getFilters(), null, 0);
        return items.getItem();
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
