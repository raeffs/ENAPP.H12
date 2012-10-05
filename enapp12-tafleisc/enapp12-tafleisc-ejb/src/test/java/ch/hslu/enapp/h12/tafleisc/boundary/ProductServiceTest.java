package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.control.ProductFacade;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class ProductServiceTest {
    
    private IProductService sut;
    
    @Mock
    private ProductFacade productFacade;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUpProductFacade();
        sut = new ProductService(productFacade);
    }
    
    @Test
    public void getProducts_hasProducts_returnsAllProducts() {
        int expected = 3;
        Collection<Product> actual = sut.getProducts();
        assertEquals(expected, actual.size());
    }
    
    private void setUpProductFacade() {
        List<ProductEntity> products = new ArrayList<ProductEntity>();
        products.add(new ProductEntity(1));
        products.add(new ProductEntity(2));
        products.add(new ProductEntity(3));
        when(productFacade.findAll()).thenReturn(products);
    }

}
