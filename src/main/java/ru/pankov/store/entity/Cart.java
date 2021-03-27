package ru.pankov.store.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    @Column(name = "price")
    private BigDecimal totalPrice;

    public void add(CartItem cartItem) {
        this.items.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem ci : items) {
            totalPrice = totalPrice.add(ci.getPrice());
        }
    }
}
