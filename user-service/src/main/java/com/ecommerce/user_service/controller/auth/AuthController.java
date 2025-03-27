package com.ecommerce.user_service.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.user_service.dto.user.UserLoginDto;
import com.ecommerce.user_service.dto.user.UserRegisterDto;
import com.ecommerce.user_service.service.auth.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(authService.register(userRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authService.login(userLoginDto));
    }
}
