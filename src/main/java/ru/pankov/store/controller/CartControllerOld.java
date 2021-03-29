package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.bean.CartOld;
import ru.pankov.store.dto.CartDTOOld;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartControllerOld {
    private final CartOld cartOld;

    @GetMapping
    public CartDTOOld getCartOld() {
        return new CartDTOOld(cartOld);
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartOld.addToCart(id);
    }

    @GetMapping("/rm/{id}")
    public void rmFromCart(@PathVariable Long id) {
        cartOld.removeFromCart(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartOld.clear();
    }

    @GetMapping("/inc/{id}")
    public void inc(@PathVariable Long id) {
        cartOld.inc(id);
    }

    @GetMapping("/dec/{id}")
    public void dec(@PathVariable Long id) {
        cartOld.dec(id);
    }
}
