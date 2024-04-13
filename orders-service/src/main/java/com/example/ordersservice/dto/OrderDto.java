package com.example.ordersservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

}
