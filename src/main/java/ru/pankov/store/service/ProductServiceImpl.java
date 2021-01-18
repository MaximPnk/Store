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
    public boolean save(ProductDTO productDTO) {
        Product p = new Product(productDTO);
        if (p.getId() != null) {
            Optional<Product> found = productRepository.findById(p.getId());
            if (!found.isPresent()) {
                return false;
            }
            p.setCreatedAt(found.get().getCreatedAt());
        }
        productRepository.save(p);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(ProductDTO productDTO) {
        Optional<Product> p = productRepository.findById(productDTO.getId());
        if (p.isPresent()) {
            productRepository.delete(p.get());
            return true;
        } else {
            return false;
        }
    }


}
