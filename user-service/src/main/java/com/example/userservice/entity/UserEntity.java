package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 50)
    private String email;
    @Column(nullable = false , length = 50)
    private String name;
    @Column(nullable = false , unique = true)
    private String userId;
    @Column(nullable = false , unique = true)
    private String encryptedPwd;

    @Builder
    public UserEntity(Long id, String email, String name, String userId, String encryptedPwd) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.encryptedPwd = encryptedPwd;
    }
}
