package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CartDTO {
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;

    public CartDTO(Cart cart) {
        this.totalPrice = cart.getTotalPrice();
        this.items = cart.getItems().stream().map(CartItemDTO::new).collect(Collectors.toList());
    }
}