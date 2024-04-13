package com.example.userservice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestLogin {

    private String email;
    private String password;
}
