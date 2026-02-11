package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.ProductNotFound;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    Random rng;
    final int seed = 0;

    @BeforeEach
    void setUp() {
        rng = new Random(seed);
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    private Product[] createNDummyProducts(int N) {
        Product[] dummyProducts = new Product[N];
        for (int i = 0; i < N; i++) {
            Product product = new Product();
            product.setProductName("Product" + i);
            product.setProductQuantity(rng.nextInt(0, 1000000));
            dummyProducts[i] = product;
            productRepository.create(product);
        }
        return dummyProducts;
    }

    @Test
    void testFindProductByIdFromMoreThanOneProduct() throws ProductNotFound {
        Product[] product = createNDummyProducts(3);

        assertDoesNotThrow(() -> productRepository.findById(product[0].getProductId()));
        assertDoesNotThrow(() -> productRepository.findById(product[1].getProductId()));
        assertDoesNotThrow(() -> productRepository.findById(product[2].getProductId()));

        assertEquals(product[0], productRepository.findById(product[0].getProductId()));
        assertEquals(product[1], productRepository.findById(product[1].getProductId()));
        assertEquals(product[2], productRepository.findById(product[2].getProductId()));

        Product newProduct = new Product();

        assertThrows(ProductNotFound.class, () -> productRepository.findById(newProduct.getProductId()));
    }

    @Test
    void testFindProductByIdFromEmptyRepository() {
        Product product = new Product();
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product.getProductId()));
    }

    @Test
    void testRemoveProductByIdFromMoreThanOneProduct() throws ProductNotFound {
        Product[] product = createNDummyProducts(3);

        assertDoesNotThrow(() -> productRepository.removeById(product[0].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product[0].getProductId()));
        assertDoesNotThrow(() -> productRepository.findById(product[1].getProductId()));
        assertDoesNotThrow(() -> productRepository.findById(product[2].getProductId()));

        assertDoesNotThrow(() -> productRepository.removeById(product[1].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product[0].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product[1].getProductId()));
        assertDoesNotThrow(() -> productRepository.findById(product[2].getProductId()));

        assertDoesNotThrow(() -> productRepository.removeById(product[2].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product[0].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product[1].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.findById(product[2].getProductId()));

        assertThrows(ProductNotFound.class, () -> productRepository.removeById(product[0].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.removeById(product[1].getProductId()));
        assertThrows(ProductNotFound.class, () -> productRepository.removeById(product[2].getProductId()));
    }

    @Test
    void testRemoveProductByIdFromEmptyRepository() {
        Product product = new Product();
        assertThrows(ProductNotFound.class, () -> productRepository.removeById(product.getProductId()));
    }
}