package ru.pankov.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pankov.store.entity.Order;
import ru.pankov.store.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    @Query("from Order o join fetch o.orderItem where o.id = ?1")
    Optional<Order> findByIdFetchOrderItems(Long id);

}
