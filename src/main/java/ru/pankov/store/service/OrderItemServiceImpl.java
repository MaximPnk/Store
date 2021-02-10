package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.OrderItemRepository;
import ru.pankov.store.entity.OrderItem;
import ru.pankov.store.service.inter.OrderItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public void saveOrUpdate(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
