package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.dao.ProductRepository;
import ru.pankov.store.entity.Product;

import java.util.List;

@RestController
@RequestMapping("api/test/products")
@RequiredArgsConstructor
public class TestProductController {

    /*
     * Test Product class with all fields in Postman
     * find all: http://localhost:8189/api/test/products/
     * find one: http://localhost:8189/api/test/products/{id}
     */

    private final ProductRepository productRepository;

    @GetMapping("/")
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }
}
