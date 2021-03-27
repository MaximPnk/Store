package ru.pankov.store.service.inter;

import ru.pankov.store.entity.Cart;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface CartService {

    @Transactional
    Cart save(Cart cart);

    @Transactional
    Optional<Cart> findById(UUID id);

    @Transactional
    void addToCart(UUID cartId, Long productId);

    @Transactional
    void removeFromCart(UUID cartId, Long productId);

    @Transactional
    void clear(UUID cartId);

    @Transactional
    void inc(UUID cartId, Long productId);

    @Transactional
    void dec(UUID cartId, Long productId);
}
