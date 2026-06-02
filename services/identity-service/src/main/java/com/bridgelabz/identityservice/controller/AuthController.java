package com.bridgelabz.identityservice.controller;

import com.bridgelabz.identityservice.dto.LoginRequest;
import com.bridgelabz.identityservice.dto.RegisterRequest;
import com.bridgelabz.identityservice.dto.TokenRefreshRequest;
import com.bridgelabz.identityservice.dto.TokenRefreshResponse;
import com.bridgelabz.identityservice.entity.RefreshToken;
import com.bridgelabz.identityservice.entity.User;
import com.bridgelabz.identityservice.exception.TokenRefreshException;
import com.bridgelabz.identityservice.repository.UserRepository;
import com.bridgelabz.identityservice.security.JwtUtils;
import com.bridgelabz.identityservice.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    // We'll need a PasswordEncoder Bean in SecurityConfig later.
    // For now we'll inject it.
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        User user = new User(request.getEmail(), request.getName(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    String jwt = jwtUtils.generateJwtToken(user.getEmail());
                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
                    Map<String, String> response = new HashMap<>();
                    response.put("token", jwt);
                    response.put("refreshToken", refreshToken.getRawToken());
                    response.put("email", user.getEmail());
                    response.put("name", user.getName());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(401)
                        .body((Map<String, String>) Map.of("error", "Invalid email or password")));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtToken(user.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
