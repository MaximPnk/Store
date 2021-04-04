package ru.pankov.store.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Column(name = "price")
    private BigDecimal totalPrice;

    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void add(CartItem cartItem) {
        for (CartItem ci : this.items) {
            if (ci.getProduct().getId().equals(cartItem.getProduct().getId())) {
                ci.incrementQuantity(cartItem.getQuantity());
                recalculate();
                return;
            }
        }

        this.items.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem ci : items) {
            ci.recalculate();
            totalPrice = totalPrice.add(ci.getPrice());
        }
    }

}
