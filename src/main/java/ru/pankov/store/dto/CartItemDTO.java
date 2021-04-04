package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.CartItem;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CartItemDTO {
    private Long id;
    private String title;
    private Integer quantity;
    private BigDecimal itemPrice;
    private BigDecimal price;

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getProduct().getId();
        this.title = cartItem.getProduct().getTitle();
        this.quantity = cartItem.getQuantity();
        this.itemPrice = cartItem.getItemPrice();
        this.price = cartItem.getPrice();
    }
}
