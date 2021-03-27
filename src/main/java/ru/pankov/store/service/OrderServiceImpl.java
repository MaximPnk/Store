package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.store.bean.CartOld;
import ru.pankov.store.dao.OrderRepository;
import ru.pankov.store.dto.OrderDTO;
import ru.pankov.store.dto.OrderItemDTO;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.OrderItem;
import ru.pankov.store.entity.User;
import ru.pankov.store.err.InsufficientNumberOfItemsException;
import ru.pankov.store.service.inter.OrderItemService;
import ru.pankov.store.service.inter.OrderService;
import ru.pankov.store.service.inter.ProductService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartOld cartOld;
    private final OrderItemService orderItemService;
    private final ProductService productService;

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
    public void makeOrder(User user, String address) {
        if (cartOld.getProducts().size() > 0) {
            cartOld.recalculate();
            Optional<OrderItem> orderItem = cartOld.getProducts().stream().filter(oi -> oi.getQuantity() > oi.getProduct().getCount()).findFirst();
            if (orderItem.isPresent()) {
                throw new InsufficientNumberOfItemsException(
                        "Insufficient number of " + orderItem.get().getProduct().getTitle() + ". Maximum is " + orderItem.get().getProduct().getCount());
            }

            Order order = new Order(user, cartOld.getTotalPrice(), address);
            saveOrUpdate(order);

            for (OrderItem oi : cartOld.getProducts()) {
                oi.setOrder(order);
                oi.getProduct().setCount(oi.getProduct().getCount() - oi.getQuantity());
                orderItemService.saveOrUpdate(oi);
                productService.save(oi.getProduct());
            }

            cartOld.clear();
        }
    }
}
