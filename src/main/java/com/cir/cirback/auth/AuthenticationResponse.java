package com.cir.cirback.auth;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
}