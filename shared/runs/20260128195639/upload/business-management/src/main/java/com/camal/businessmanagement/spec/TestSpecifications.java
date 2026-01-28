package com.camal.businessmanagement.spec;

import com.camal.businessmanagement.entity.Test;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Map;

/**
 * JPA Specifications for dynamic Test queries.
 */
public final class TestSpecifications {

    private TestSpecifications() {
    }

    public static Specification<Test> fromParams(Map<String, String> params) {
        Specification<Test> spec = Specification.where(notDeleted());

        String q = params.get("q");
        if (isNotBlank(q)) {
            String pattern = "%" + q.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("url")), pattern),
                    cb.like(cb.lower(root.get("productName")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            ));
        }

        String url = params.get("url");
        if (isNotBlank(url)) {
            String pattern = "%" + url.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("url")), pattern));
        }

        String productName = params.get("productName");
        if (isNotBlank(productName)) {
            String pattern = "%" + productName.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("productName")), pattern));
        }

        spec = addBigDecimalFilter(spec, params, "boughtPrice");
        spec = addBigDecimalRangeFilter(spec, params, "boughtPrice", "minBoughtPrice", "maxBoughtPrice");
        spec = addBigDecimalFilter(spec, params, "sellPrice");
        spec = addBigDecimalRangeFilter(spec, params, "sellPrice", "minSellPrice", "maxSellPrice");

        String description = params.get("description");
        if (isNotBlank(description)) {
            String pattern = "%" + description.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("description")), pattern));
        }

        return spec;
    }

    public static Specification<Test> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    private static Specification<Test> addBigDecimalFilter(
            Specification<Test> spec, Map<String, String> params, String fieldName) {
        String value = params.get(fieldName);
        if (isNotBlank(value)) {
            BigDecimal v = new BigDecimal(value.trim());
            spec = spec.and((root, query, cb) -> cb.equal(root.get(fieldName), v));
        }
        return spec;
    }

    private static Specification<Test> addBigDecimalRangeFilter(
            Specification<Test> spec, Map<String, String> params,
            String fieldName, String minParamName, String maxParamName) {
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
