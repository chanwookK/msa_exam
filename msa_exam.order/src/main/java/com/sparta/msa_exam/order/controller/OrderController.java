package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.request.OrderAddProductRequest;
import com.sparta.msa_exam.order.dto.request.OrderAddRequest;
import com.sparta.msa_exam.order.dto.response.OrderAddProductResponse;
import com.sparta.msa_exam.order.dto.response.OrderAddResponse;
import com.sparta.msa_exam.order.dto.response.OrderGetResponse;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderAddResponse addOrder(@RequestBody OrderAddRequest order){
        return orderService.addOrder(order);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderAddProductResponse addProductInOrder(@RequestBody OrderAddProductRequest product, @PathVariable Long orderId){
        return orderService.addProductInOrder(product, orderId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderGetResponse getOrder(@PathVariable Long orderId){
        return orderService.getOrder(orderId);
    }
}
