package ru.pankov.store.dao.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.pankov.store.entity.Product;

import java.math.BigDecimal;
import java.util.Locale;

public class ProductSpecification {

    private static Specification<Product> priceGreaterOrEqualsThan(BigDecimal min) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min));
    }

    private static Specification<Product> priceLesserOrEqualsThan(BigDecimal max) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
    }

    private static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), String.format("%%%s%%", titlePart.toLowerCase(Locale.ROOT)));
    }

    private static Specification<Product> countGreaterThan() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("count"), 0);
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
        if (params.containsKey("present") && !params.getFirst("present").isEmpty() && params.getFirst("present").equals("true")) {
            spec = spec.and(ProductSpecification.countGreaterThan());
        }
        return spec;
    }
}
