package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.product.ProductClient;
import com.sparta.msa_exam.order.client.product.dto.ProductListResponse;
import com.sparta.msa_exam.order.domain.Order;
import com.sparta.msa_exam.order.domain.OrderProduct;
import com.sparta.msa_exam.order.dto.request.OrderAddProductRequest;
import com.sparta.msa_exam.order.dto.request.OrderAddRequest;
import com.sparta.msa_exam.order.dto.response.OrderAddProductResponse;
import com.sparta.msa_exam.order.dto.response.OrderAddResponse;
import com.sparta.msa_exam.order.dto.response.OrderGetResponse;
import com.sparta.msa_exam.order.exception.ApplicationException;
import com.sparta.msa_exam.order.exception.ErrorCode;
import com.sparta.msa_exam.order.repository.OrderProductRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    private final ProductClient productClient;

    public boolean isExistProduct(Long productId){
        return productId != null && productClient.getProducts().stream()
                .anyMatch(p -> Objects.equals(p.productId(), productId));
    }

    public List<Long> existProducts(List<Long> productIds){
        Set<Long> existProductIds = productClient.getProducts().stream()
                .map(ProductListResponse::productId)  // productId 추출
                .collect(Collectors.toSet()); // Set으로 수집

        return productIds.stream()
                .filter(existProductIds::contains)
                .toList();
    }

    @Transactional
    @CircuitBreaker(name = "OrderService", fallbackMethod = "addOrderFallBackMethod")
    public OrderAddResponse addOrder(OrderAddRequest orderAddRequest) {

        // 주문 생성 로직 구현
        Order savedOrder = orderRepository.save(new Order());

        existProducts(orderAddRequest.productIdList())
                .forEach(productId ->
                    orderProductRepository.save(
                            OrderProduct.builder()
                                    .order(savedOrder)
                                    .productId(productId)
                                    .build()
                            )
                );

        return new OrderAddResponse(savedOrder.getId());
    }

    public String addOrderFallBackMethod(){
        return "잠시 후 다시 요청해주세요";
    }

    @Transactional
    public OrderAddProductResponse addProductInOrder(OrderAddProductRequest orderAddProductRequest, Long orderId) {
        if(!isExistProduct(orderAddProductRequest.productId())){
            throw new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

        orderProductRepository.save(
                OrderProduct.builder()
                        .order(order)
                        .productId(orderAddProductRequest.productId())
                        .build()
        );

        return new OrderAddProductResponse(orderId);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "orderGetOneCache", key = "args[0]")
    public OrderGetResponse getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

        List<Long> productIds = orderProductRepository.findAllByOrder(order)
                .stream()
                .map(OrderProduct::getProductId)
                .toList();

        return new OrderGetResponse(order.getId(), productIds);
    }

}
