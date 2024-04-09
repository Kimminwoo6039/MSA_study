package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());


       UserEntity userEntity =  UserEntity.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .userId(userDto.getUserId())
                .encryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()))
                .build();
        userRepository.save(userEntity);

        return null;
    }
}
