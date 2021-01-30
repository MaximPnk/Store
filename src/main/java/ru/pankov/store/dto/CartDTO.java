package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.bean.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CartDTO {

    private List<OrderItemDTO> products;
    private BigDecimal totalPrice;

    public CartDTO(Cart cart) {
        this.totalPrice = cart.getTotalPrice();
        this.products = cart.getProducts().stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }
}
