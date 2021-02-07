package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.bean.Cart;
import ru.pankov.store.dto.CartDTO;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;

    @GetMapping
    public CartDTO getCart() {
        return new CartDTO(cart);
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        System.out.println("+");
        cart.addToCart(id);
    }

    @GetMapping("/rm/{id}")
    public void rmFromCart(@PathVariable Long id) {
        cart.removeFromCart(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cart.clear();
    }

    @GetMapping("/inc/{id}")
    public void inc(@PathVariable Long id) {
        cart.inc(id);
    }

    @GetMapping("/dec/{id}")
    public void dec(@PathVariable Long id) {
        cart.dec(id);
    }
}
