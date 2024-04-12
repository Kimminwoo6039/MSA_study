package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw  new UsernameNotFoundException("user not found");
        }

        UserDto userDto = UserDto.builder()
                .email(userEntity.getEmail())
                .pwd(userEntity.getEncryptedPwd())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .build();

        List<ResponseOrder> orderList = new ArrayList<>();
        userDto.setOrders(orderList);

        return userDto;


    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
