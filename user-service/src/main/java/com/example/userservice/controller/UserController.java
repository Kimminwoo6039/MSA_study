package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping("/health_check")
    public String status() {
        return "lt's working in user service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return environment.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser requestUser) {

        UserDto user = UserDto.builder()
                .email(requestUser.getEmail())
                .pwd(requestUser.getPwd())
                .name(requestUser.getName())
                .build();

        ResponseUser responseUser = ResponseUser.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .build();

        userService.createUser(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

}
