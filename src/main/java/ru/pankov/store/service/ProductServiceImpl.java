package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.ProductRepository;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.entity.Product;
import ru.pankov.store.service.inter.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ru.pankov.store.soap.Product> findAllSoap() {
        return findAll().stream().map(p -> {
            ru.pankov.store.soap.Product soapProduct = new ru.pankov.store.soap.Product();
            soapProduct.setId(p.getId());
            soapProduct.setTitle(p.getTitle());
            soapProduct.setPrice(p.getPrice());
            soapProduct.setCount(p.getCount());
            return soapProduct;
        }).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Page<ProductDTO> findAll(Specification<Product> spec, Integer page, Integer limit) {
        Page<Product> productsPage = productRepository.findAll(spec, PageRequest.of(page - 1, limit));
        return productsPage.map(ProductDTO::new);
    }

    @Override
    public Optional<ProductDTO> findProductDTOById(Long id) {
        return productRepository.findById(id).map(ProductDTO::new);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
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
