package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pankov.store.bean.Cart;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.OrderItem;
import ru.pankov.store.entity.User;
import ru.pankov.store.service.inter.OrderItemService;
import ru.pankov.store.service.inter.OrderService;
import ru.pankov.store.service.inter.ProductService;
import ru.pankov.store.service.inter.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final Cart cart;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    //TODO на бэке работает, но фронт после makeOrder() не ждёт .then и обновляет страницу до изменений (???)

    //TODO проверить корректность работы корзины в сессиях между юзерами

    @GetMapping("/")
    public void makeOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Order order = new Order(user);
        orderService.saveOrUpdate(order);

        for (OrderItem oi : cart.getProducts()) {
            oi.setPrice(oi.getProduct().getPrice());
            oi.setOrder(order);
            oi.getProduct().setCount(oi.getProduct().getCount() - oi.getQuantity());
            orderItemService.saveOrUpdate(oi);
            productService.save(oi.getProduct());
        }

        cart.clear();
    }
}
