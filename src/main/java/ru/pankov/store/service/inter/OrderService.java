package ru.pankov.store.service.inter;

import ru.pankov.store.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    void saveOrUpdate(Order order);
}
