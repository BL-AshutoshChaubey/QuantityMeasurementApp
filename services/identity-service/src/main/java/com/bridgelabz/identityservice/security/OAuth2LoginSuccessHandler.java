package com.bridgelabz.identityservice.security;

import com.bridgelabz.identityservice.entity.RefreshToken;
import com.bridgelabz.identityservice.entity.User;
import com.bridgelabz.identityservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @org.springframework.beans.factory.annotation.Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtUtils jwtUtils,
            RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Save new user if they don't exist
        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User(email, name);
            return userRepository.save(newUser);
        });

        // Generate JWT and Refresh Token
        String token = jwtUtils.generateJwtToken(email);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

        // Instantly redirect back to the live React frontend application with active
        // JWT token, refresh token, and profile name
        String displayName = name != null ? name : email;
        String encodedName = java.net.URLEncoder.encode(displayName, java.nio.charset.StandardCharsets.UTF_8);
        
        String redirectUrl = frontendUrl.endsWith("/") ? frontendUrl.substring(0, frontendUrl.length() - 1) : frontendUrl;
        response.sendRedirect(redirectUrl + "/?token=" + token + "&refreshToken=" + refreshToken.getRawToken()
                + "&username=" + encodedName);
    }
}
