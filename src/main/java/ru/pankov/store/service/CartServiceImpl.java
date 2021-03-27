package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.CartRepository;
import ru.pankov.store.entity.Cart;
import ru.pankov.store.entity.CartItem;
import ru.pankov.store.entity.Product;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.CartService;
import ru.pankov.store.service.inter.ProductService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        return cartRepository.findById(id);
    }

    @Transactional
    @Override
    public void addToCart(UUID cartId, Long productId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        List<CartItem> cartItemList = cart.getItems();

        for (CartItem ci : cartItemList) {
            if (ci.getProduct().getId().equals(productId)) {
                ci.incrementQuantity();
                cart.recalculate();
                return;
            }
        }

        Product p = productService.findProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + productId + " (add to cart)"));
        cart.add(new CartItem(p));
        cart.recalculate();
    }

    @Transactional
    @Override
    public void removeFromCart(UUID cartId, Long productId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        for (CartItem ci : cart.getItems()) {
            if (ci.getProduct().getId().equals(productId)) {
                cart.getItems().remove(ci);
                cart.recalculate();
                return;
            }
        }
    }

    @Transactional
    @Override
    public void clear(UUID cartId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        cart.getItems().clear();
        cart.recalculate();
    }

    @Transactional
    @Override
    public void inc(UUID cartId, Long productId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        for (CartItem ci : cart.getItems()) {
            if (ci.getProduct().getId().equals(productId)) {
                ci.incrementQuantity();
                cart.recalculate();
                return;
            }
        }
    }

    @Transactional
    @Override
    public void dec(UUID cartId, Long productId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        for (CartItem ci : cart.getItems()) {
            if (ci.getProduct().getId().equals(productId)) {
                if (ci.getQuantity() <= 1) {
                    cart.getItems().remove(ci);
                } else {
                    ci.decrementQuantity();
                }
                cart.recalculate();
                return;
            }
        }
    }
}
