package ru.pankov.store.service;

import org.springframework.data.domain.Page;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
//    List<Product> findAll(BigDecimal min, BigDecimal max);

    Page<ProductDTO> findAll(BigDecimal min, BigDecimal max, int page);

    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);
}
