package ru.pankov.store.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetween(BigDecimal min, BigDecimal max);

    Page<Product> findAllByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);
}
