package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.ProductNotFound;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Product findById(String id) throws ProductNotFound {
        return productData.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElseThrow(ProductNotFound::new);
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void removeById(String id) throws ProductNotFound {
        boolean removed = productData.removeIf(p -> p.getProductId().equals(id));
        if (!removed) {
            throw new ProductNotFound();
        }
    }

}
