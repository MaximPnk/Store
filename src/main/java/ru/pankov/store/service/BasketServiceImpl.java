package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.BasketRepository;
import ru.pankov.store.entity.ProductInBasket;
import ru.pankov.store.entity.Product;
import ru.pankov.store.service.inter.BasketService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    @Override
    public List<ProductInBasket> findAllByUser(Long id) {
        return basketRepository.findAllByUser(id);
    }

    @Override
    @Transactional
    public void deleteAllByUser(Long id) {
        basketRepository.deleteAllByUser(id);
    }

    @Override
    public void save(Long userId, Product p) {
        if (!findByUserAndProduct(userId, p).isPresent()) {
            basketRepository.save(new ProductInBasket(userId, p));
        }
    }

    @Override
    public void remove(ProductInBasket p) {
        basketRepository.delete(p);
    }

    @Override
    public void updateCount(ProductInBasket p) {
        if (p.getCount() <= 0) {
            remove(p);
        } else {
            basketRepository.save(p);
        }
    }

    @Override
    public Optional<ProductInBasket> findByUserAndProduct(Long userId, Product p) {
        return basketRepository.findFirstByUserAndProduct(userId, p);
    }
}
