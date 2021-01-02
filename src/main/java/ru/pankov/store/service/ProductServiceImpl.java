package ru.pankov.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankov.store.dao.ProductDAO;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    
    @Override
    public List<Product> findAll(BigDecimal min, BigDecimal max) {
        return productDAO.findAllByPriceBetween(min, max);
    }

    @Override
    public Product findById(Long id) {

        Optional<Product> product = productDAO.findById(id);
        Product result = null;

        if (product.isPresent()) {
            result = product.get();
        }

        return result;
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public Product deleteById(Long id) {

        Product product = findById(id);

        if (product == null) {
            throw new RuntimeException("Product with id " + id + " not found");
        }

        productDAO.deleteById(id);

        return product;
    }


}
