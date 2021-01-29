package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pankov.store.entity.Product;
import ru.pankov.store.entity.ProductInBasket;
import ru.pankov.store.service.inter.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/")
    public List<ProductInBasket> getProducts(@RequestParam Long userId) {
        return basketService.findAllByUser(userId);
    }

    @PostMapping("/")
    public void addProduct(@RequestParam Long userId, @RequestBody Product p) {
        basketService.save(userId, p);
    }

    @PutMapping("/")
    public void update(@RequestBody ProductInBasket p) {
        basketService.updateCount(p);
    }

    @DeleteMapping("/{userId}")
    public void clearBasket(@PathVariable(value="userId") Long userId) {
        basketService.deleteAllByUser(userId);
    }

    @DeleteMapping("/")
    public void removeProduct(@RequestBody ProductInBasket p) {
        basketService.remove(p);
    }

}
