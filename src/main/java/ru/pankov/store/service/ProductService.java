package ru.pankov.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.entity.Product;

import java.util.Optional;

public interface ProductService {

    Page<ProductDTO> findAll(Specification<Product> spec, Integer page, Integer limit);

    Optional<ProductDTO> findById(Long id);

    boolean save(ProductDTO productDTO);

    boolean deleteById(Long id);

    boolean delete(ProductDTO productDTO);
}
