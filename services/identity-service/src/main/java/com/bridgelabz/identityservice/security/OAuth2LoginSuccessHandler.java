package com.bridgelabz.identityservice.security;

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

    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Save new user if they don't exist
        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User(email, name);
            return userRepository.save(newUser);
        });

        // Generate JWT
        String token = jwtUtils.generateJwtToken(email);

        // Redirect back to the React frontend application (dynamically based on environment)
        String frontendUrl = System.getenv("FRONTEND_URL");
        if (frontendUrl == null || frontendUrl.isEmpty()) {
            frontendUrl = "http://localhost:5173";
        }
        
        String displayName = name != null ? name : email;
        String encodedName = java.net.URLEncoder.encode(displayName, java.nio.charset.StandardCharsets.UTF_8);
        response.sendRedirect(frontendUrl + "/?token=" + token + "&username=" + encodedName);
    }
}
