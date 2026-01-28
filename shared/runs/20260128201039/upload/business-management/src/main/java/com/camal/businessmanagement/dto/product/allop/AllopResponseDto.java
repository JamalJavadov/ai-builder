package com.camal.businessmanagement.dto.product.allop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllopResponseDto {
    private Long id;
    private Long version;
    private String url;
    private String productName;
    private BigDecimal boughtPrice;
    private BigDecimal sellPrice;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
