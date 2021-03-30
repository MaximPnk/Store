package ru.pankov.store.service.inter;

import ru.pankov.store.dto.OrderDTO;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    List<OrderDTO> findAllForAdmins();

    List<OrderDTO> findAllForCustomers(User user);

    Optional<Order> findOrderById(Long id);

    Optional<OrderDTO> findByIdWithOrderItems(Long id);

    void saveOrUpdate(Order order);

    void makeOrder(String userName, UUID cartId, String address);
}
