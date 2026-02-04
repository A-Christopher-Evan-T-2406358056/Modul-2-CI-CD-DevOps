package id.ac.ui.cs.advprog.eshop.exception;

public class ProductNotFound extends Exception {
    public ProductNotFound() {
        super("Product not found in repository");
    }
}
