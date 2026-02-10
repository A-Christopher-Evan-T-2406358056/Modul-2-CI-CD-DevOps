package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
        assertNotEquals("eb558e9f-1c39-460e-5555-71af6af63bd6", this.product.getProductId());
        assertNotEquals("", this.product.getProductId());
        assertNotNull(this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
        assertNotEquals("sampo cap bambang", this.product.getProductName());
        assertNotEquals("", this.product.getProductName());
        assertNotNull(this.product.getProductName());
    }

    @Test
    void testSetProductId() {
        String expectedId = "eb558e9f-1c39-460e-5555-71af6af63bd6";
        product.setProductId(expectedId);
        assertEquals(expectedId, product.getProductId());
    }

    @Test
    void testSetProductName() {
        String expectedName = "sampo cap gambamg";
        product.setProductName(expectedName);
        assertEquals(expectedName, product.getProductName());
    }

    @Test
    void testSetProductQuantity() {
        int expectedQuantity = 523;
        product.setProductQuantity(expectedQuantity);
        assertEquals(expectedQuantity, product.getProductQuantity());
    }
}