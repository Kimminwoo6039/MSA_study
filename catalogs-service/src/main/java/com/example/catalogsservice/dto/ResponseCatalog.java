package com.example.catalogsservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private Date createdAt;
}
