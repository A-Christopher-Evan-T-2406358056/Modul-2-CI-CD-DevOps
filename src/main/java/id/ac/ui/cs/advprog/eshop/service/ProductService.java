package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.exception.ProductNotFound;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.ProductDto;

import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public Product edit(ProductDto productDto) throws ProductNotFound;
    public List<Product> findAll();
}
