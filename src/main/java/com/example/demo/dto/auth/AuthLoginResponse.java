package com.example.demo.dto.auth;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuthLoginResponse {
    private boolean success;
    private String msg;
    private String token;
    private AuthResult result;
}