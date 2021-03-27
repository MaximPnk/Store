package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pankov.store.dto.CartDTO;
import ru.pankov.store.entity.Cart;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public UUID createCart() {
        Cart cart = cartService.save(new Cart());
        return cart.getId();
    }

    @GetMapping("/{cartId}")
    public CartDTO getCart(@PathVariable UUID cartId) {
        return new CartDTO(cartService.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Not found")));
    }

    @GetMapping("/add/{cartId}/{productId}")
    public void addToCart(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.addToCart(cartId, productId);
    }

    @GetMapping("/rm/{cartId}/{productId}")
    public void rmFromCart(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.removeFromCart(cartId, productId);
    }

    @GetMapping("/clear/{cartId}")
    public void clearCart(@PathVariable UUID cartId) {
        cartService.clear(cartId);
    }

    @GetMapping("/inc/{cartId}/{productId}")
    public void inc(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.inc(cartId, productId);
    }

    @GetMapping("/dec/{cartId}/{productId}")
    public void dec(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.dec(cartId, productId);
    }


}
