package com.example.userservice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
