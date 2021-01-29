package ru.pankov.store.service.inter;

import ru.pankov.store.entity.ProductInBasket;
import ru.pankov.store.entity.Product;

import java.util.List;
import java.util.Optional;

public interface BasketService {

    List<ProductInBasket> findAllByUser(Long id);

    void deleteAllByUser(Long id);

    void save(Long userId, Product p);

    void remove(ProductInBasket p);

    void updateCount(ProductInBasket p);

    Optional<ProductInBasket> findByUserAndProduct(Long userId, Product p);
}
