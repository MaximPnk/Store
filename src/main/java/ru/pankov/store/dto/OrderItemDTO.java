package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.OrderItem;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class OrderItemDTO {
    private Long id;
    private String title;
    private Integer quantity;
    private BigDecimal itemPrice;
    private BigDecimal price;

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getProduct().getId();
        this.title = orderItem.getProduct().getTitle();
        this.quantity = orderItem.getQuantity();
        this.itemPrice = orderItem.getItemPrice();
        this.price = orderItem.getPrice();
    }
}
