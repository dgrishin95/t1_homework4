package com.mysite.t1_homework4.service;

import com.mysite.t1_homework4.dto.AuthenticationResponse;
import com.mysite.t1_homework4.dto.UserLoginRequest;
import com.mysite.t1_homework4.dto.UserRegisterRequest;
import com.mysite.t1_homework4.model.Token;
import com.mysite.t1_homework4.model.User;
import com.mysite.t1_homework4.repository.TokenRepository;
import com.mysite.t1_homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(UserRegisterRequest userRegisterRequest) {

        if (userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        User user = User.builder()
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .role(userRegisterRequest.getRole())
                .build();

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful");
    }

    public AuthenticationResponse authenticate(UserLoginRequest userLoginRequest) {
        User user = User.builder()
                .username(userLoginRequest.getUsername())
                .password(userLoginRequest.getPassword())
                .build();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()));

        user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");
    }

    @Transactional
    protected void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());

        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> t.setLoggedOut(true));

        tokenRepository.saveAll(validTokens);
    }

    @Transactional
    protected void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
