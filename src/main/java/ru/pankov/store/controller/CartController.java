package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pankov.store.dto.CartDTO;
import ru.pankov.store.entity.Cart;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/")
    public UUID createCart() {
        Cart cart = cartService.save(new Cart());
        return cart.getId();
    }

    @GetMapping("exists/{cartId}")
    public boolean cartExist(@PathVariable UUID cartId) {
        return cartService.cartExists(cartId);
    }

    @GetMapping("/{cartId}")
    public CartDTO getCart(@PathVariable UUID cartId) {
        return new CartDTO(cartService.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Not found")));
    }

    @PostMapping("/add")
    public void addToCart(@RequestParam(name = "uuid") UUID cartId, @RequestParam(name = "product_id") Long productId) {
        cartService.addToCart(cartId, productId);
    }

    @DeleteMapping("/rm")
    public void rmFromCart(@RequestParam(name = "uuid") UUID cartId, @RequestParam(name = "product_id") Long productId) {
        cartService.removeFromCart(cartId, productId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestParam(name = "uuid") UUID cartId) {
        cartService.clear(cartId);
    }

    @PutMapping("/inc")
    public void inc(@RequestParam(name = "uuid") UUID cartId, @RequestParam(name = "product_id") Long productId) {
        cartService.inc(cartId, productId);
    }

    @PutMapping("/dec")
    public void dec(@RequestParam(name = "uuid") UUID cartId, @RequestParam(name = "product_id") Long productId) {
        cartService.dec(cartId, productId);
    }


}
