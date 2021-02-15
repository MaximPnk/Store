package ru.pankov.store.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.dao.OrderItemRepository;
import ru.pankov.store.entity.OrderItem;

import java.util.List;

@RestController
@RequestMapping("api/test/orderItems")
@RequiredArgsConstructor
public class TestOrderItemController {

    private final OrderItemRepository orderItemRepository;

    @GetMapping("/")
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public OrderItem findById(@PathVariable Long id) {
        return orderItemRepository.findById(id).get();
    }
}
