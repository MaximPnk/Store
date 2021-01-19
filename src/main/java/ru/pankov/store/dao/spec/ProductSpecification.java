package ru.pankov.store.dao.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    private static Specification<Product> priceGreaterOrEqualsThan(BigDecimal min) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min));
    }

    private static Specification<Product> priceLesserOrEqualsThan(BigDecimal max) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
    }

    //TODO root.get to lower case
    private static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        if (params.containsKey("min") && !params.getFirst("min").isEmpty()) {
            spec = spec.and(ProductSpecification.priceGreaterOrEqualsThan(new BigDecimal(params.getFirst("min"))));
        }
        if (params.containsKey("max") && !params.getFirst("max").isEmpty()) {
            spec = spec.and(ProductSpecification.priceLesserOrEqualsThan(new BigDecimal(params.getFirst("max"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isEmpty()) {
            spec = spec.and(ProductSpecification.titleLike(params.getFirst("title")));
        }
        return spec;
    }
}
