package ru.pankov.store.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updated_at;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.itemPrice = product.getPrice();
        this.price = this.itemPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    public void recalculate() {
        itemPrice = product.getPrice();
        price = itemPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void incrementQuantity() {
        quantity++;
        price = price.add(itemPrice);
    }

    public void decrementQuantity() {
        quantity--;
        price = price.subtract(itemPrice);
    }
}
