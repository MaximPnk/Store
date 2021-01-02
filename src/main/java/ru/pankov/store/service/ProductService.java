package ru.pankov.store.service;

import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findAll(BigDecimal min, BigDecimal max);

    Product findById(Long id);

    void save(Product product);

    Product deleteById(Long id);
}
