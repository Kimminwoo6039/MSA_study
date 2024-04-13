package com.example.ordersservice.controller;

import com.example.ordersservice.dto.OrderDto;
import com.example.ordersservice.dto.RequestOrder;
import com.example.ordersservice.dto.ResponseOrder;
import com.example.ordersservice.entity.OrderEntity;
import com.example.ordersservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId ,
                                                     @RequestBody RequestOrder orderDetails
    ) {
        OrderDto orderDto = OrderDto.builder()
                .productId(orderDetails.getProductId())
                .qty(orderDetails.getQty())
                .unitPrice(orderDetails.getUnitPrice())
                .build();

        orderDto.setUserId(userId);

        OrderDto createDto = orderService.createOrder(orderDto);

        ResponseOrder result = ResponseOrder.builder()
                .orderId(createDto.getOrderId())
                .totalPrice(createDto.getTotalPrice())
                .qty(createDto.getQty())
                .productId(createDto.getProductId())
                .unitPrice(createDto.getUnitPrice())
                .orderId(createDto.getOrderId())
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result =  new ArrayList<>();

        orderList.forEach(v->{
            result.add(
                    ResponseOrder.builder()
                            .orderId(v.getOrderId())
                            .qty(v.getQty())
                            .totalPrice(v.getTotalPrice())
                            .createdAt(v.getCreatedAt())
                            .unitPrice(v.getUnitPrice())
                            .productId(v.getProductId())
                            .build()
            );
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
