package ru.pankov.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pankov.store.entity.Cart;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Modifying
    @Query("delete from Cart c where c.user is null and c.updatedAt < :expireDate")
    void deleteExpiredGuestCarts(LocalDateTime expireDate);

    // another option
    // void deleteAllByUserIsNullAndUpdatedAtBefore(LocalDateTime date);
}
