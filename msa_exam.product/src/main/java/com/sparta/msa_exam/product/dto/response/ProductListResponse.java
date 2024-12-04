package com.sparta.msa_exam.product.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;


@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductListResponse implements Serializable {
    Long productId;
    String name;
    Integer supplyPrice;
}
