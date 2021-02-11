package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class OrderDTOForAdmins {
    private long id;
    private long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private LocalDateTime updatedAt;
    private BigDecimal price;

    public OrderDTOForAdmins(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userName = order.getUser().getUsername();
        this.userEmail = order.getUser().getEmail();
        this.userPhone = order.getUser().getPhone();
        this.updatedAt = order.getUpdatedAt();
        this.price = order.getPrice();
    }
}
