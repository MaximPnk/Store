package ru.pankov.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.store.entity.Product;
import ru.pankov.store.entity.ProductInBasket;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<ProductInBasket, Long> {

    List<ProductInBasket> findAllByUser(Long id);

    Optional<ProductInBasket> findFirstByUserAndProduct(Long userId, Product product);

    void deleteAllByUser(Long id);
}
