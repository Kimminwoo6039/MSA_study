package com.example.userservice.repository;

import com.example.userservice.entity.UserEntity;
import com.example.userservice.security.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity member = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("이미 존재")
        );
        return new LoginUser(member);
    }
}
