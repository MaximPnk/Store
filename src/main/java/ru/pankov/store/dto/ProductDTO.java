package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer count;

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.count = p.getCount();
    }
}
