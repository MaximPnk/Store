package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.ProductRepository;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    
    /*@Override
    public List<Product> findAll(BigDecimal min, BigDecimal max) {
        return productRepository.findAllByPriceBetween(min, max);
    }*/

    @Override
    public Page<ProductDTO> findAll(BigDecimal min, BigDecimal max, int page) {
        Page<Product> productsPage = productRepository.findAllByPriceBetween(min, max, PageRequest.of(page - 1, 5));
        return productsPage.map(ProductDTO::new);
        /*return new PageImpl<>(
                productsPage.getContent().stream().map(ProductDTO::new).collect(Collectors.toList()),
                productsPage.getPageable(),
                productsPage.getTotalElements()
        );*/
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


}
