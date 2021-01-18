package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.service.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public Page<ProductDTO> productList(@RequestParam(value = "min", defaultValue = "0") BigDecimal min,
                                        @RequestParam(value = "max", defaultValue = "999999") BigDecimal max,
                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit") Integer limit) {
        return productService.findAll(min, max, page, limit);
    }

    @GetMapping("/{id}")
    public ProductDTO product(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new RuntimeException("There is no product with id = " + id));
    }

    @PostMapping("/")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return productDTO;
    }

    @PutMapping("/")
    public ProductDTO updProduct(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return productDTO;
    }

    @DeleteMapping("/{id}")
    public void delProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @DeleteMapping("/")
    public void delProduct(@RequestBody ProductDTO productDTO) {
        productService.delete(productDTO);
    }
}
