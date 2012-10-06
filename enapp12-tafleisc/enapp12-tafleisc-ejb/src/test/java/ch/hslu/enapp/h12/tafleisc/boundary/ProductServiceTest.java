package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.dto.Product;
import ch.hslu.enapp.h12.tafleisc.boundary.mapping.ProductMapper;
import ch.hslu.enapp.h12.tafleisc.control.ProductFacade;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public class ProductServiceTest {

    private IProductService sut;
    @Mock
    private ProductFacade productFacade;
    @Mock
    private ProductMapper productMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUpProductFacade();
        setUpProductMapper();
        sut = new ProductService(productFacade, productMapper);
    }

    @Test
    public void getProducts_hasProducts_returnsAllProducts() {
        int expected = 3;
        Collection<Product> actual = sut.getProducts();
        assertEquals(expected, actual.size());
    }

    @Test
    public void getProducts_hasProducts_callsMapperForEachProduct() {
        int expected = 3;
        sut.getProducts();
        verify(productMapper, VerificationModeFactory.times(expected))
                .mapEntityToDto(any(ProductEntity.class));
    }

    private void setUpProductFacade() {
        List<ProductEntity> products = new ArrayList<ProductEntity>();
        products.add(new ProductEntity(1));
        products.add(new ProductEntity(2));
        products.add(new ProductEntity(3));
        when(productFacade.findAll()).thenReturn(products);
    }

    private void setUpProductMapper() {
        when(productMapper.mapEntityToDto(any(ProductEntity.class)))
                .thenReturn(new Product());
    }
}
