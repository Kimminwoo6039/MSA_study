package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping("/health_check")
    public String status() {
        return "lt's working in user service on Port %s".formatted(environment.getProperty("local.server.port"));
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
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add(
                    ResponseUser.builder()
                            .userId(v.getUserId())
                            .email(v.getEmail())
                            .name(v.getName())
                            .build()
            );
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable(value = "userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);


        ResponseUser result = ResponseUser.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
