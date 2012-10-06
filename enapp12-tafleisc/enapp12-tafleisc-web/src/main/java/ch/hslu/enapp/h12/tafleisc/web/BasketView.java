package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.IPurchaseService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Named(value = "basketView")
@SessionScoped
public class BasketView implements Serializable {

    @Inject
    private IPurchaseService purchaseService;

    public IPurchaseService getPurchaseService() {
        return purchaseService;
    }
}
