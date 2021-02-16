package ru.pankov.store.service.inter;

import ru.pankov.store.dto.OrderDTO;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.User;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderService {

    List<OrderDTO> findAllForAdmins();

    List<OrderDTO> findAllForCustomers(User user);

    void saveOrUpdate(Order order);

    void makeOrder(User user, String address);
}
