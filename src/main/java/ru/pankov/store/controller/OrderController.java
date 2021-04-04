package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pankov.store.dto.OrderDTO;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.Role;
import ru.pankov.store.entity.User;
import ru.pankov.store.err.MarketError;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.OrderService;
import ru.pankov.store.service.inter.RoleService;
import ru.pankov.store.service.inter.UserService;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final RoleService roleService;
    private Role adminRole;

    @PostConstruct
    public void init() {
        adminRole = roleService.findByName("ROLE_ADMIN").orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderFetchOrderItems(Principal principal, @PathVariable Long id) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderService.findOrderById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!user.getRoles().contains(adminRole) && !order.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(new MarketError(HttpStatus.FORBIDDEN.value(), "Forbidden"), HttpStatus.FORBIDDEN);
        } else {
            return ResponseEntity.ok(orderService.findByIdWithOrderItems(id).get());
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void makeOrder(Principal principal, @RequestParam(name = "uuid") UUID cartId, @RequestParam(name = "address") String address) {
        log.info("Make order: username = " + principal.getName() + ", cart id = " + cartId + ", address = " + address);
        orderService.makeOrder(principal.getName(), cartId, address);
    }
}
