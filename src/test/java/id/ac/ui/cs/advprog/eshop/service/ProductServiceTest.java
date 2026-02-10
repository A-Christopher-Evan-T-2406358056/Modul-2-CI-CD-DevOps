package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.exception.ProductNotFound;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.ProductDto;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    ProductRepository productRepository;

    ProductServiceTest() {
        productRepository = new ProductRepository();
        productService = new ProductServiceImpl(productRepository);
    }

    ProductServiceImpl productService;
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
        productService.create(product);

        List<Product> productsList = productService.findAll();
        assertEquals(1, productsList.size());

        Product savedProduct = productsList.getFirst();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Product> productList = productService.findAll();
        assertTrue(productList.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productService.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productService.create(product2);

        List<Product> productList = productService.findAll();
        assertEquals(2, productList.size());

        assertEquals(product1.getProductId(), productList.getFirst().getProductId());
        assertEquals(product2.getProductId(), productList.getLast().getProductId());
    }

    private Product[] createNDummyProducts(int N) {
        Product[] dummyProducts = new Product[N];
        for (int i = 0; i < N; i++) {
            Product product = new Product();
            product.setProductName("Product" + i);
            product.setProductQuantity(rng.nextInt(0, 1000000));
            dummyProducts[i] = product;
            productService.create(product);
        }
        return dummyProducts;
    }

    @Test
    void testEditProductByIdFromMoreThanOneProduct() throws ProductNotFound {
        Product[] product = createNDummyProducts(3);

        String name = "test";
        int quantity = 103;
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product[1].getProductId());
        productDto.setProductName(name);

        assertDoesNotThrow(() -> productService.edit(productDto));
        assertEquals("test", product[1].getProductName());

        productDto.setProductQuantity(quantity);
        assertDoesNotThrow(() -> productService.edit(productDto));
        assertEquals(quantity, product[1].getProductQuantity());

        Product newProduct = new Product();
        ProductDto newProductDto = new ProductDto();
        newProductDto.setProductId(newProduct.getProductId());

        assertThrows(ProductNotFound.class, () -> productService.edit(newProductDto));
    }

    @Test
    void testEditProductByIdFromEmptyRepository() {
        Product newProduct = new Product();
        ProductDto newProductDto = new ProductDto();
        newProductDto.setProductId(newProduct.getProductId());
        assertThrows(ProductNotFound.class, () -> productService.edit(newProductDto));
    }

    @Test
    void testRemoveProductByIdFromMoreThanOneProduct() throws ProductNotFound {
        Product[] product = createNDummyProducts(3);

        assertDoesNotThrow(() -> productService.removeById(product[0].getProductId()));

        List<Product> productList = productService.findAll();
        assertEquals(2, productList.size());
        assertFalse(productList.contains(product[0]));
        assertTrue(productList.contains(product[1]));
        assertTrue(productList.contains(product[2]));

        assertDoesNotThrow(() -> productService.removeById(product[1].getProductId()));

        productList = productService.findAll();
        assertEquals(1, productList.size());
        assertFalse(productList.contains(product[0]));
        assertFalse(productList.contains(product[1]));
        assertTrue(productList.contains(product[2]));

        assertDoesNotThrow(() -> productService.removeById(product[2].getProductId()));

        productList = productService.findAll();
        assertEquals(0, productList.size());

        assertThrows(ProductNotFound.class, () -> productService.removeById(product[0].getProductId()));
        assertThrows(ProductNotFound.class, () -> productService.removeById(product[1].getProductId()));
        assertThrows(ProductNotFound.class, () -> productService.removeById(product[2].getProductId()));
    }

    @Test
    void testRemoveProductByIdFromEmptyRepository() {
        Product product = new Product();
        assertThrows(ProductNotFound.class, () -> productService.removeById(product.getProductId()));
    }
}