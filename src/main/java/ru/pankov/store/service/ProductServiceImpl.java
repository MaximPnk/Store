package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.ProductRepository;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<ProductDTO> findAll(BigDecimal min, BigDecimal max, Integer page, Integer limit) {
        Page<Product> productsPage = productRepository.findAllByPriceBetween(min, max, PageRequest.of(page - 1, limit));
        return productsPage.map(ProductDTO::new);
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(ProductDTO::new);
    }

    @Override
    public void save(ProductDTO productDTO) {
        Product p = new Product(productDTO);
        if (p.getId() != null) {
            p.setCreatedAt(productRepository.findById(p.getId()).orElseThrow(() -> new RuntimeException("Invalid product")).getCreatedAt());
        }
        productRepository.save(p);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void delete(ProductDTO productDTO) {
        productRepository.delete(productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("Invalid product")));
    }


}
