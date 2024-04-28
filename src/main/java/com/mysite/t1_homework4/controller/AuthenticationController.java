package com.mysite.t1_homework4.controller;

import com.mysite.t1_homework4.dto.AuthenticationResponse;
import com.mysite.t1_homework4.dto.UserLoginRequest;
import com.mysite.t1_homework4.dto.UserRegisterRequest;
import com.mysite.t1_homework4.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authService.register(userRegisterRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.authenticate(userLoginRequest));
    }
}
