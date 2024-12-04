package com.sparta.msa_exam.product.converter;

import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.dto.request.ProductAddRequest;
import com.sparta.msa_exam.product.dto.response.ProductAddResponse;
import com.sparta.msa_exam.product.dto.response.ProductListResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public ProductListResponse toProductListResponse(Product productEntity){
        return ProductListResponse.builder()
                .productId(productEntity.getId())
                .name(productEntity.getName())
                .supplyPrice(productEntity.getSupplyPrice())
                .build();
    }

    public Product toProductEntity(ProductAddRequest productAddRequest){
        return Product.builder()
                .name(productAddRequest.name())
                .supplyPrice(productAddRequest.supplyPrice())
                .build();
    }

    public ProductAddResponse toProductAddResponse(Product productEntity){
        return ProductAddResponse.builder()
                .productId(productEntity.getId())
                .build();
    }

}
