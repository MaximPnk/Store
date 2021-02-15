package ru.pankov.store.service.inter;

import ru.pankov.store.dto.OrderDTOForAdmins;
import ru.pankov.store.dto.OrderDTOForCustomers;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDTOForAdmins> findAll();

    void saveOrUpdate(Order order);

    @Transactional
    void makeOrder(User user);

    List<OrderDTOForCustomers> findAllByUser(User user);
}
