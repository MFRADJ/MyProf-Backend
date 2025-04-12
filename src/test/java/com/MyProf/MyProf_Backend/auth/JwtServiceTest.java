package com.MyProf.MyProf_Backend.auth;

import com.MyProf.MyProf_Backend.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    @Spy
    private JwtService jwtService;

    private final String secretKey = "YmFzZTY0ZW5jb2RlZHNlY3JldGtleWZvcnRlc3Rz"; // Example Base64 secret
    private final long jwtExpiration = 3600000L; // 1 hour

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the private getSignInKey method
        doReturn(Keys.hmacShaKeyFor(secretKey.getBytes())).when(jwtService).getSignInKey();
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String username = "testUser";

        // Generate a token
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        // Extract the username
        String extractedUsername = jwtService.extractUsername(token);

        // Assert the extracted username matches the original username
        assertEquals(username, extractedUsername);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        User userDetails = new User("testUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Generate token
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);

        // Extract username from the generated token
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userDetails.getUsername(), extractedUsername);
    }

    @Test
    void generateTokenWithExtraClaims_ShouldIncludeClaims() {
        User userDetails = new User("testUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Map<String, Object> extraClaims = Map.of("customClaim", "claimValue");

        // Generate token with extra claims
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Extract all claims
        Claims claims = jwtService.extractAllClaims(token);

        // Assert the custom claim exists
        assertEquals("claimValue", claims.get("customClaim"));
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        User userDetails = new User("testUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Generate token
        String token = jwtService.generateToken(userDetails);

        // Validate the token
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalseForInvalidToken() {
        User userDetails = new User("testUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);

        // Create another user
        User anotherUserDetails = new User("anotherUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Validate the token with another user's details
        boolean isValid = jwtService.isTokenValid(token, anotherUserDetails);

        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_ShouldReturnTrueForExpiredToken() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // Expired
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        boolean isExpired = jwtService.isTokenValid(token, new User("testUser", "password", List.of()));

        assertFalse(isExpired); // Token is invalid because it's expired
    }

    @Test
    void extractAllClaims_ShouldReturnAllClaims() {
        Map<String, Object> extraClaims = Map.of("claimKey", "claimValue");
        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        Claims claims = jwtService.extractAllClaims(token);

        assertEquals("claimValue", claims.get("claimKey"));
    }
}

