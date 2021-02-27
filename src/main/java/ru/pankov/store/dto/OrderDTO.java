package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

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
    private String address;
    private Collection<OrderItemDTO> orderItems;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userName = order.getUser().getUsername();
        this.userEmail = order.getUser().getEmail();
        this.userPhone = order.getUser().getPhone();
        this.createdAt = order.getCreatedAt();
        this.price = order.getPrice();
        this.address = order.getAddress();
    }

    public OrderDTO(long id, LocalDateTime createdAt, BigDecimal price, String address) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
        this.address = address;
    }

    public OrderDTO(long id, LocalDateTime createdAt, BigDecimal price, String address, Collection<OrderItemDTO> orderItems) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
        this.address = address;
        this.orderItems = orderItems;
    }
}
