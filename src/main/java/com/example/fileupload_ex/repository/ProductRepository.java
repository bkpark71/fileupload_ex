package com.example.fileupload_ex.repository;

import com.example.fileupload_ex.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    private long sequence = 0L;

    public void save(Product product) {
        product.setId(++sequence);
        products.put(product.getId(), product);
    }

    public Product findById(Long id) {
        Product product = products.get(id);
        return product;
    }

}
