package com.example.ordersservice.service;

import com.example.ordersservice.dto.OrderDto;
import com.example.ordersservice.entity.OrderEntity;
import com.example.ordersservice.respository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrdersRepository ordersRepository;


    @Override
    public OrderDto createOrder(OrderDto orderDetails) {

        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderDetails.getProductId())
                .qty(orderDetails.getQty())
                .userId(orderDetails.getUserId())
                .orderId(orderDetails.getOrderId())
                .totalPrice(orderDetails.getTotalPrice())
                .unitPrice(orderDetails.getUnitPrice())
                .build();

        ordersRepository.save(orderEntity);

        OrderDto orderDto = OrderDto.builder()
                .userId(orderEntity.getUserId())
                .totalPrice(orderEntity.getTotalPrice())
                .orderId(orderEntity.getOrderId())
                .qty(orderEntity.getQty())
                .unitPrice(orderEntity.getUnitPrice())
                .productId(orderEntity.getProductId())
                .build();


        return orderDto;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEnt = ordersRepository.findByOrderId(orderId);

        OrderDto orderDto = OrderDto.builder()
                .productId(orderEnt.getProductId())
                .qty(orderEnt.getQty())
                .unitPrice(orderEnt.getUnitPrice())
                .orderId(orderEnt.getOrderId())
                .totalPrice(orderEnt.getTotalPrice())
                .userId(orderEnt.getUserId())
                .build();

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return ordersRepository.findByUserId(userId);
    }
}
