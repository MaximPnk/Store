package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.dao.OrderRepository;
import ru.pankov.store.entity.Order;

import java.util.List;

@RestController
@RequestMapping("api/test/orders")
@RequiredArgsConstructor
public class TestOrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/")
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderRepository.findById(id).get();
    }
}
