package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class OrderDTO {
    private long id;
    private long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private LocalDateTime createdAt;
    private BigDecimal price;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userName = order.getUser().getUsername();
        this.userEmail = order.getUser().getEmail();
        this.userPhone = order.getUser().getPhone();
        this.createdAt = order.getCreatedAt();
        this.price = order.getPrice();
    }

    public OrderDTO(long id, LocalDateTime createdAt, BigDecimal price) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
    }
}
