package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.OrderRepository;
import ru.pankov.store.dto.OrderDTO;
import ru.pankov.store.dto.OrderItemDTO;
import ru.pankov.store.entity.*;
import ru.pankov.store.err.InsufficientNumberOfItemsException;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;

    @Override
    public List<OrderDTO> findAllForAdmins() {
        return orderRepository.findAll().stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAllForCustomers(User user) {
        return orderRepository.findAllByUser(user).stream().map(u -> new OrderDTO(u.getId(), u.getCreatedAt(), u.getPrice(), u.getAddress())).collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<OrderDTO> findByIdWithOrderItems(Long id) {
        return orderRepository.findByIdFetchOrderItems(id)
                .map(u -> new OrderDTO(u.getId(), u.getCreatedAt(), u.getPrice(), u.getAddress(), u.getOrderItem().stream().map(OrderItemDTO::new).collect(Collectors.toList())));
    }

    @Override
    public void saveOrUpdate(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void makeOrder(String userName, UUID cartId, String address) {

        User user = userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartService.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().size() > 0) {
            Optional<CartItem> cartItem = cart.getItems().stream().filter(ci -> ci.getQuantity() > ci.getProduct().getCount()).findFirst();
            if (cartItem.isPresent()) {
                throw new InsufficientNumberOfItemsException(
                        "Insufficient number of " + cartItem.get().getProduct().getTitle() + ". Maximum is " + cartItem.get().getProduct().getCount());
            }

            Order order = orderRepository.save(new Order(user, cart.getTotalPrice(), address));

            for (CartItem ci : cart.getItems()) {
                OrderItem oi = new OrderItem(ci);
                oi.setOrder(order);
                oi.getProduct().setCount(oi.getProduct().getCount() - oi.getQuantity());
                orderItemService.saveOrUpdate(oi);
                productService.save(oi.getProduct());
            }

            cartService.clear(cart.getId());
        }
    }
}
