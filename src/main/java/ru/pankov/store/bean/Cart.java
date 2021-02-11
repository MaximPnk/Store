package ru.pankov.store.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.pankov.store.entity.OrderItem;
import ru.pankov.store.entity.Product;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.ProductService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    private final ProductService productService;
    private List<OrderItem> products;
    private BigDecimal totalPrice;

    @PostConstruct
    public void init() {
        this.products = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }

    public void addToCart(Long id) {
        for (OrderItem oi : products) {
            if (oi.getProduct().getId().equals(id)) {
                oi.incrementQuantity();
                recalculate();
                return;
            }
        }
        Product p = productService.findProductById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + id + " (add to cart)"));
        OrderItem orderItem = new OrderItem(p);
        products.add(orderItem);
        recalculate();
    }

    public void removeFromCart(Long id) {
        for (OrderItem oi : products) {
            if (oi.getProduct().getId().equals(id)) {
                products.remove(oi);
                recalculate();
                return;
            }
        }
    }

    public void clear() {
        products.clear();
        recalculate();
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (OrderItem oi : products) {
            oi.setProduct(productService.findProductById(oi.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("There is no product with id = " + oi.getProduct().getId())));
            oi.recalculate();
            totalPrice = totalPrice.add(oi.getPrice());
        }
    }

    public void inc(Long id) {
        for (OrderItem oi : products) {
            if (oi.getProduct().getId().equals(id)) {
                oi.incrementQuantity();
                recalculate();
                return;
            }
        }
    }

    public void dec(Long id) {
        for (OrderItem oi : products) {
            if (oi.getProduct().getId().equals(id)) {
                if (oi.getQuantity() <= 1) {
                    products.remove(oi);
                } else {
                    oi.decrementQuantity();
                }
                recalculate();
                return;
            }
        }
    }

}
