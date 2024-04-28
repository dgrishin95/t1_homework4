package com.mysite.t1_homework4.service;

import com.mysite.t1_homework4.dto.AuthenticationResponse;
import com.mysite.t1_homework4.dto.UserLoginRequest;
import com.mysite.t1_homework4.dto.UserRegisterRequest;
import com.mysite.t1_homework4.model.Role;
import com.mysite.t1_homework4.model.User;
import com.mysite.t1_homework4.repository.TokenRepository;
import com.mysite.t1_homework4.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void testRegisterSuccess() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("First name", "Last name",
                "Username", "Password", Role.ADMIN);
        User user = User.builder()
                .firstName("First name")
                .lastName("Last name")
                .username("Username")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .build();

        Mockito.when(userRepository.findByUsername("First name")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("takenToken");

        AuthenticationResponse response = authenticationService.register(userRegisterRequest);

        assertEquals("takenToken", response.getToken());
        assertEquals("User registration was successful", response.getMessage());
    }

    @Test
    public void testAuthenticateSuccess() {
        UserLoginRequest userLoginRequest = new UserLoginRequest("First name", "password");
        User user = User.builder()
                .username("First name")
                .password("encodedPassword")
                .build();

        Mockito.when(userRepository.findByUsername("First name")).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(user)).thenReturn("takenToken");

        AuthenticationResponse response = authenticationService.authenticate(userLoginRequest);

        assertEquals("takenToken", response.getToken());
        assertEquals("User login was successful", response.getMessage());
    }
}
