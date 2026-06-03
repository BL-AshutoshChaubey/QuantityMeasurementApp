package com.bridgelabz.identityservice.security;

import com.bridgelabz.identityservice.entity.RefreshToken;
import com.bridgelabz.identityservice.entity.User;
import com.bridgelabz.identityservice.exception.TokenRefreshException;
import com.bridgelabz.identityservice.repository.RefreshTokenRepository;
import com.bridgelabz.identityservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private User testUser;

    @BeforeEach
    public void setup() {
        // Set standard 24 hour refresh token duration
        ReflectionTestUtils.setField(refreshTokenService, "refreshTokenDurationMs", 86400000L);
        
        testUser = new User("test@example.com", "Test User", "password");
        testUser.setId(1L);
    }

    @Test
    public void testCreateRefreshToken() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(refreshTokenRepository.findByUser(testUser)).thenReturn(Optional.empty()); // No existing token
        when(jwtUtils.sha256(any(String.class))).thenReturn("mocked_sha256_hash");
        
        // Mock save to return the same entity back
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken token = refreshTokenService.createRefreshToken(testUser.getEmail());

        assertNotNull(token);
        assertEquals(testUser, token.getUser());
        assertEquals("mocked_sha256_hash", token.getToken());
        assertNotNull(token.getRawToken(), "Raw token must not be null to send back to client");
        assertTrue(token.getExpiryDate().isAfter(Instant.now()), "Expiry date should be in the future");
        
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    public void testFindByToken() {
        String rawToken = "my-raw-uuid-token";
        String hashedToken = "hashed-token-from-db";
        
        RefreshToken mockToken = new RefreshToken();
        mockToken.setToken(hashedToken);
        
        when(jwtUtils.sha256(rawToken)).thenReturn(hashedToken);
        when(refreshTokenRepository.findByToken(hashedToken)).thenReturn(Optional.of(mockToken));

        Optional<RefreshToken> foundToken = refreshTokenService.findByToken(rawToken);

        assertTrue(foundToken.isPresent());
        assertEquals(hashedToken, foundToken.get().getToken());
        verify(jwtUtils, times(1)).sha256(rawToken);
    }

    @Test
    public void testVerifyExpirationNotExpired() {
        RefreshToken token = new RefreshToken();
        token.setExpiryDate(Instant.now().plusMillis(10000)); // Expires in 10 seconds

        RefreshToken verifiedToken = refreshTokenService.verifyExpiration(token);
        
        assertEquals(token, verifiedToken);
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    public void testVerifyExpirationExpired() {
        RefreshToken token = new RefreshToken();
        token.setToken("expired-hash");
        token.setExpiryDate(Instant.now().minusMillis(10000)); // Expired 10 seconds ago

        TokenRefreshException exception = assertThrows(TokenRefreshException.class, () -> {
            refreshTokenService.verifyExpiration(token);
        });

        assertTrue(exception.getMessage().contains("Refresh token was expired"));
        verify(refreshTokenRepository, times(1)).delete(token); // Verify it gets deleted from DB
    }
}
