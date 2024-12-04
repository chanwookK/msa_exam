package com.sparta.msa_exam.product.controller;


import com.sparta.msa_exam.product.dto.request.ProductAddRequest;
import com.sparta.msa_exam.product.dto.response.ProductAddResponse;
import com.sparta.msa_exam.product.dto.response.ProductListResponse;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public List<ProductListResponse> getProducts() {
        return productService.getProductList();
    }

    @PostMapping("")
    public ProductAddResponse addProduct(@RequestBody ProductAddRequest product) {
        return productService.addProduct(product);
    }

}
