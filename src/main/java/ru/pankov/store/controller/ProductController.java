package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.pankov.store.dao.spec.ProductSpecification;
import ru.pankov.store.dto.ProductDTO;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.ProductService;
import ru.pankov.store.service.inter.UserService;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public Page<ProductDTO> productList(@RequestParam MultiValueMap<String, String> params,
                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        return productService.findAll(ProductSpecification.build(params), page, limit);
    }

    @GetMapping("/{id}")
    public ProductDTO product(@PathVariable Long id) {
        return productService.findProductDTOById(id).orElseThrow(() -> new ResourceNotFoundException("There is no product with id = " + id));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        if (!productService.save(productDTO)) {
            throw new ResourceNotFoundException("There is no product with id = " + productDTO.getId());
        }
        return productDTO;
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO updProduct(@RequestBody ProductDTO productDTO) {
        if (!productService.save(productDTO)) {
            throw new ResourceNotFoundException("There is no product with id = " + productDTO.getId());
        }
        return productDTO;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delProduct(@PathVariable Long id) {
        if (!productService.deleteById(id)) {
            throw new ResourceNotFoundException("There is no product with id = " + id);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delProduct(@RequestBody ProductDTO productDTO) {
        if (!productService.delete(productDTO)) {
            throw new ResourceNotFoundException("There is no product with id = " + productDTO.getId());
        }
    }
}
