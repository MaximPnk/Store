package ru.pankov.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.store.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
