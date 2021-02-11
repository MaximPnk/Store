package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.store.bean.Cart;
import ru.pankov.store.dao.OrderRepository;
import ru.pankov.store.dto.OrderDTOForAdmins;
import ru.pankov.store.dto.OrderDTOForCustomers;
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
    private final Cart cart;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    @Override
    public List<OrderDTOForAdmins> findAll() {
        return orderRepository.findAll().stream().map(OrderDTOForAdmins::new).collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void saveOrUpdate(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void makeOrder(User user) {
        if (cart.getProducts().size() > 0) {
            cart.recalculate();
            Optional<OrderItem> orderItem = cart.getProducts().stream().filter(oi -> oi.getQuantity() > oi.getProduct().getCount()).findFirst();
            if (orderItem.isPresent()) {
                throw new InsufficientNumberOfItemsException(
                        "Insufficient number of " + orderItem.get().getProduct().getTitle() + ". Maximum is " + orderItem.get().getProduct().getCount());
            }

            Order order = new Order(user, cart.getTotalPrice());
            saveOrUpdate(order);

            for (OrderItem oi : cart.getProducts()) {
                oi.setOrder(order);
                oi.getProduct().setCount(oi.getProduct().getCount() - oi.getQuantity());
                orderItemService.saveOrUpdate(oi);
                productService.save(oi.getProduct());
            }

            cart.clear();
        }
    }

    @Override
    public List<OrderDTOForCustomers> findAllByUser(User user) {
        return orderRepository.findAllByUser(user).stream().map(OrderDTOForCustomers::new).collect(Collectors.toList());

    }
}
