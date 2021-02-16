package ru.pankov.store.service.inter;

import ru.pankov.store.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findAll();

    void saveOrUpdate(OrderItem orderItem);
}
