package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.dto.OrderDTO;
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

    @GetMapping("/curr")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public List<OrderDTO> getOrdersByUser(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderService.findAllForCustomers(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrderDTO> getOrdersByUser() {
        return orderService.findAllForAdmins();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void makeOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.makeOrder(user);
    }
}
