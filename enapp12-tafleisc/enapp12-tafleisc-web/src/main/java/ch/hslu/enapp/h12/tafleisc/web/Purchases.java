package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.ICustomerService;
import ch.hslu.enapp.h12.tafleisc.boundary.IPurchaseService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Purchase;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@ManagedBean(name = "purchases")
@RequestScoped
public class Purchases {

    @Inject
    private ICustomerService customerService;
    @Inject
    private IPurchaseService purchaseService;
    private List<Purchase> purchases;

    public Collection<Purchase> getPurchases() {
        if (purchases == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            int customerId = customerService.getCustomerId(principal.getName());
            purchases = new ArrayList<Purchase>(purchaseService.getCustomerPurchases(customerId));
            Collections.sort(purchases);
        }
        return purchases;
    }
}
