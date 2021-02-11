package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.dto.OrderDTOForAdmins;
import ru.pankov.store.dto.OrderDTOForCustomers;
import ru.pankov.store.entity.User;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.OrderService;
import ru.pankov.store.service.inter.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    //TODO рефакторинг и удаление лишнего фронта
    //TODO добавить юзера и посмотреть как они работают с корзиной и заказами
    //TODO регистрация

    @GetMapping("/curr")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public List<OrderDTOForCustomers> getOrdersByUser(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderService.findAllByUser(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrderDTOForAdmins> getOrdersByUser() {
        return orderService.findAll();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void makeOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.makeOrder(user);
    }
}
