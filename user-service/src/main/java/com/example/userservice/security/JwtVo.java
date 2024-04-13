package com.example.userservice.security;

public interface JwtVo {
    
    public static String SECRET = "__Relean__Server";
    public static final int EXPIRATTION_TIME = 30 * 60 * 60; // 일주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

}
