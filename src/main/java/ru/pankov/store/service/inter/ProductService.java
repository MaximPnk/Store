package ru.pankov.store.service.inter;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    List<ru.pankov.store.soap.Product> findAllSoap();

    void save(Product product);

    Page<ProductDTO> findAll(Specification<Product> spec, Integer page, Integer limit);

    Optional<ProductDTO> findProductDTOById(Long id);

    Optional<Product> findProductById(Long id);

    boolean save(ProductDTO productDTO);

    boolean deleteById(Long id);

    boolean delete(ProductDTO productDTO);
}
