package com.camal.businessmanagement.dto.product.guard;

import lombok.*;

@jakarta.annotation.Generated("java-project-crud.py")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuardRequestDto {
    private String url;
    private String productName;
    private Double boughtPrice;
    private Double sellPrice;
    private String description;
}
