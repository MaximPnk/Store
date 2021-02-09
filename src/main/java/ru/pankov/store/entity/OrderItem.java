package ru.pankov.store.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

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
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.itemPrice = product.getPrice();
        this.price = this.itemPrice.multiply(BigDecimal.valueOf(this.quantity));
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
