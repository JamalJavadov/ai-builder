package com.camal.businessmanagement.dto.product.test;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
public class TestCreateRequestDto {

    @Size(max = 500, message = "URL must not exceed 500 characters")
    private String url;

    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String productName;

    @NotNull(message = "Bought price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Bought price must be non-negative")
    private BigDecimal boughtPrice;

    @NotNull(message = "Sell price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Sell price must be non-negative")
    private BigDecimal sellPrice;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;
}
