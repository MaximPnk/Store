package ru.pankov.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
    }
}
