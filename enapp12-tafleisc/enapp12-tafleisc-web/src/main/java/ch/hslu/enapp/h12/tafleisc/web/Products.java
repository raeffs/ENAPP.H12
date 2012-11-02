package ch.hslu.enapp.h12.tafleisc.web;

import ch.hslu.enapp.h12.tafleisc.boundary.IProductService;
import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Named(value = "products")
@RequestScoped
public class Products {

    @Inject @Named("ErpProductService")
    private IProductService productService;
    @Inject
    private Basket basketView;
    private Collection<ProductModel> products;

    public Collection<ProductModel> getProducts() {
        if (products == null) {
            products = new ArrayList<ProductModel>();
            loadProducts();
        }
        return products;
    }

    private void loadProducts() {
        for (Product dto : productService.getProducts()) {
            ProductModel model = new ProductModel(dto);
            products.add(model);
        }
    }

    public void addButtonClicked(ActionEvent event)
            throws InvalidProductException, InvalidQuantityException, IOException {
        for (ProductModel product : products) {
            if (product.isSelected()) {
                basketView.getPurchaseService().addProductToBasket(product.getId(), 1);
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("./basket.xhtml");
    }
}
