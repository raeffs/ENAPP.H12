package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.IPurchaseService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.PurchaseItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Named(value = "basket")
@SessionScoped
public class BasketViewModel implements Serializable {

    @Inject
    private IPurchaseService purchaseService;

    public IPurchaseService getPurchaseService() {
        return purchaseService;
    }

    public Collection<PurchaseItem> getItems() {
        return purchaseService.getItemsInBasket();
    }

    public void checkoutButtenClicked(ActionEvent event)
            throws IOException {
        purchaseService.checkout(1);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./index.html");
    }
}
