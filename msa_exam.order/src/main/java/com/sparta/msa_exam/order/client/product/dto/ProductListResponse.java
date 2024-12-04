package com.sparta.msa_exam.order.client.product.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductListResponse(
        Long productId,
        String name,
        Integer supplyPrice
) {
}
