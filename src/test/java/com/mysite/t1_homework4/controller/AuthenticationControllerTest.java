package com.mysite.t1_homework4.controller;

import com.mysite.t1_homework4.dto.AuthenticationResponse;
import com.mysite.t1_homework4.dto.UserLoginRequest;
import com.mysite.t1_homework4.dto.UserRegisterRequest;
import com.mysite.t1_homework4.model.Role;
import com.mysite.t1_homework4.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authService;

    @Test
    public void testRegister() {
        UserRegisterRequest userRegisterRequest =
                new UserRegisterRequest("First name", "Last name", "Username", "Password", Role.ADMIN);
        AuthenticationResponse expectedResponse = new AuthenticationResponse("takenToken", "User registration was successful");

        Mockito.when(authService.register(userRegisterRequest)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(userRegisterRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testLogin() {
        UserLoginRequest userLoginRequest = new UserLoginRequest("Username", "Password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("takenToken", "User login was successful");

        Mockito.when(authService.authenticate(userLoginRequest)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.login(userLoginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
