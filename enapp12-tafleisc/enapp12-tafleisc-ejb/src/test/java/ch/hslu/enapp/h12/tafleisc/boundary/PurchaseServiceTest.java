package ch.hslu.enapp.h12.tafleisc.boundary;

import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidProductException;
import ch.hslu.enapp.h12.tafleisc.boundary.exceptions.InvalidQuantityException;
import ch.hslu.enapp.h12.tafleisc.control.ProductFacade;
import ch.hslu.enapp.h12.tafleisc.entity.ProductEntity;
import ch.hslu.enapp.h12.tafleisc.entity.PurchaseitemEntity;
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
public class PurchaseServiceTest {

    private IPurchaseService sut;
    @Mock
    private ProductFacade productFacade;
    @Mock
    private Collection<PurchaseitemEntity> collection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUpProductFacade();
        sut = new PurchaseService(productFacade, collection);
    }
    
    @Test
    public void addProductToBasket_productExists_addsProductToCollection() 
            throws InvalidProductException, InvalidQuantityException {
        sut.addProductToBasket(1, 1);
        verify(collection).add(any(PurchaseitemEntity.class));
    }
    
    private void setUpProductFacade() {
        when(productFacade.exists(1)).thenReturn(true);
        when(productFacade.find(1)).thenReturn(new ProductEntity(1));
    }
}
