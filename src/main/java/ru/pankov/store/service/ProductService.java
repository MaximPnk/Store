package ru.pankov.store.service;

import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll(BigDecimal min, BigDecimal max);

    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);
}
