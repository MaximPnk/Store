package ru.pankov.store.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.pankov.store.dao.CartRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CartScheduler {

    private final CartRepository cartRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    @Transactional
    public void deleteOldCarts() {
        cartRepository.deleteExpiredGuestCarts(LocalDateTime.now().minusDays(14));
    }
}
