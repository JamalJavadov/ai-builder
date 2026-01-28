package com.camal.businessmanagement.spec;

import com.camal.businessmanagement.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Map;

/**
 * JPA Specifications for dynamic Product queries.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
        // Utility class
    }

    /**
     * Builds a specification from query parameters.
     *
     * @param params query parameters map
     * @return combined specification
     */
    public static Specification<Product> fromParams(Map<String, String> params) {
        Specification<Product> spec = Specification.where(notDeleted());

        // Full-text search across multiple fields
        String q = params.get("q");
        if (isNotBlank(q)) {
            String pattern = "%" + q.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("url")), pattern),
                    cb.like(cb.lower(root.get("productName")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            ));
        }

        // URL filter
        String url = params.get("url");
        if (isNotBlank(url)) {
            String pattern = "%" + url.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("url")), pattern));
        }

        // Product name filter
        String productName = params.get("productName");
        if (isNotBlank(productName)) {
            String pattern = "%" + productName.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("productName")), pattern));
        }

        // Bought price filters
        spec = addBigDecimalFilter(spec, params, "boughtPrice");
        spec = addBigDecimalRangeFilter(spec, params, "boughtPrice", "minBoughtPrice", "maxBoughtPrice");

        // Sell price filters
        spec = addBigDecimalFilter(spec, params, "sellPrice");
        spec = addBigDecimalRangeFilter(spec, params, "sellPrice", "minSellPrice", "maxSellPrice");

        // Description filter
        String description = params.get("description");
        if (isNotBlank(description)) {
            String pattern = "%" + description.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("description")), pattern));
        }

        return spec;
    }

    /**
     * Filter for non-deleted records.
     */
    public static Specification<Product> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    private static Specification<Product> addBigDecimalFilter(
            Specification<Product> spec,
            Map<String, String> params,
            String fieldName) {

        String value = params.get(fieldName);
        if (isNotBlank(value)) {
            BigDecimal v = new BigDecimal(value.trim());
            spec = spec.and((root, query, cb) -> cb.equal(root.get(fieldName), v));
        }
        return spec;
    }

    private static Specification<Product> addBigDecimalRangeFilter(
            Specification<Product> spec,
            Map<String, String> params,
            String fieldName,
            String minParamName,
            String maxParamName) {

        String minValue = params.get(minParamName);
        if (isNotBlank(minValue)) {
            BigDecimal v = new BigDecimal(minValue.trim());
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(fieldName), v));
        }

        String maxValue = params.get(maxParamName);
        if (isNotBlank(maxValue)) {
            BigDecimal v = new BigDecimal(maxValue.trim());
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(fieldName), v));
        }

        return spec;
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
