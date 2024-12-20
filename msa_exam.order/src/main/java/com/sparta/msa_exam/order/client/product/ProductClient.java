package com.sparta.msa_exam.order.client.product;

import com.sparta.msa_exam.order.client.product.dto.ProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/products")
    List<ProductListResponse> getProducts();
}
