package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class OrderDTOForCustomers {
    private long id;
    private LocalDateTime updatedAt;
    private BigDecimal price;

    public OrderDTOForCustomers(Order order) {
        this.id = order.getId();
        this.updatedAt = order.getUpdatedAt();
        this.price = order.getPrice();
    }
}
