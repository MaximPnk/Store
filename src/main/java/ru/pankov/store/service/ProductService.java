package ru.pankov.store.service;

import org.springframework.data.domain.Page;
import ru.pankov.store.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Page<ProductDTO> findAll(BigDecimal min, BigDecimal max, Integer page, Integer limit);

    Optional<ProductDTO> findById(Long id);

    boolean save(ProductDTO productDTO);

    boolean deleteById(Long id);

    boolean delete(ProductDTO productDTO);
}
