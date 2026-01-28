package com.camal.businessmanagement.dto.product.test;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPatchRequestDto {

    @NotNull(message = "Version is required for optimistic locking")
    private Long version;

    @Size(max = 500, message = "URL must not exceed 500 characters")
    private String url;

    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String productName;

    @DecimalMin(value = "0.0", inclusive = true, message = "Bought price must be non-negative")
    private BigDecimal boughtPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "Sell price must be non-negative")
    private BigDecimal sellPrice;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;
}
