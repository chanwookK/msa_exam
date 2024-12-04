package com.sparta.msa_exam.product.service;


import com.sparta.msa_exam.product.controller.ProductController;
import com.sparta.msa_exam.product.converter.ProductConverter;
import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.dto.request.ProductAddRequest;
import com.sparta.msa_exam.product.dto.response.ProductAddResponse;
import com.sparta.msa_exam.product.dto.response.ProductListResponse;
import com.sparta.msa_exam.product.global.exception.ApplicationException;
import com.sparta.msa_exam.product.global.exception.ErrorCode;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productListCache", key = "methodName")
    public List<ProductListResponse> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::toProductListResponse)
                .toList();
    }


    @Transactional
    @CacheEvict(cacheNames = "productListCache",allEntries = true)
    public ProductAddResponse addProduct(ProductAddRequest product) {

        Product savedProduct = productRepository.save(productConverter.toProductEntity(product));

        if (savedProduct == null) {
            throw new ApplicationException(ErrorCode.INVALID_VALUE_EXCEPTION);
        }

        return productConverter.toProductAddResponse(savedProduct);
    }


}
