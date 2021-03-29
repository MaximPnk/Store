package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.bean.CartOld;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CartDTOOld {

    private List<OrderItemDTO> products;
    private BigDecimal totalPrice;

    public CartDTOOld(CartOld cartOld) {
        this.totalPrice = cartOld.getTotalPrice();
        this.products = cartOld.getProducts().stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }
}
