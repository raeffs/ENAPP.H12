package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.IProductService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Named(value = "productsView")
@RequestScoped
public class ProductsView {

    @Inject
    private IProductService productService;
    private Collection<Product> products;

    public Collection<Product> getProducts() {
        if (products == null) {
            products = productService.getProducts();
        }
        return products;
    }
}
