package com.MyProf.MyProf_Backend.auth;

import com.MyProf.MyProf_Backend.email.EmailService;
import com.MyProf.MyProf_Backend.roles.Role;
import com.MyProf.MyProf_Backend.roles.RoleRepository;
import com.MyProf.MyProf_Backend.security.JwtService;
import com.MyProf.MyProf_Backend.user.User;
import com.MyProf.MyProf_Backend.user.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthentifictionServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthentifictionService authentifictionService;

    private User testUser;
    private Role userRole;
    private RegistrationRequest registrationRequest;
    private AuthentificationRequest authentificationRequest;
    private Token testToken;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authentifictionService, "activationUrl", "http://localhost:3000/activate");

        userRole = Role.builder()
                .id("1L")
                .name("ROLE_USER")
                .build();

        testUser = User.builder()
                .id("1")
                .email("test@example.com")
                .password("hashedPassword")
                .firstName("Test")
                .lastName("User")
                .roles(List.of(userRole))
                .enabled(false)
                .accountLocked(false)
                .build();

        registrationRequest = RegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .confirmPassword("password")
                .firstName("Test")
                .lastName("User")
                .build();

        authentificationRequest = AuthentificationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        testToken = Token.builder()
                .id("1")
                .token("123456")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(testUser)
                .build();
    }

    @Test
    void whenRegister_withValidData_thenSuccess() throws MessagingException {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        assertDoesNotThrow(() -> authentifictionService.register(registrationRequest));

        verify(userRepository).save(any(User.class));
        verify(emailService).sendEmail(anyString(), anyString(), any(), anyString(), anyString(), anyString());
    }

    @Test
    void whenRegister_withPasswordMismatch_thenThrowException() {
        registrationRequest.setConfirmPassword("differentPassword");

        assertThrows(IllegalArgumentException.class,
                () -> authentifictionService.register(registrationRequest));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenAuthenticate_withValidCredentials_thenReturnToken() {
        var authToken = new UsernamePasswordAuthenticationToken(testUser.getEmail(), testUser.getPassword());
        when(authenticationManager.authenticate(any())).thenReturn(authToken);
        when(jwtService.generateToken(any(HashMap.class), any(User.class))).thenReturn("jwt-token");

        AuthentificationResponse response = authentifictionService.authenticate(authentificationRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(any(HashMap.class), any(User.class));
    }

    @Test
    void whenActivateAccount_withValidToken_thenSuccess() throws MessagingException {
        when(tokenRepository.findByToken("123456")).thenReturn(Optional.of(testToken));
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(tokenRepository.save(any(Token.class))).thenReturn(testToken);

        assertDoesNotThrow(() -> authentifictionService.activateAccount("123456"));

        verify(userRepository).save(any(User.class));
        verify(tokenRepository).save(any(Token.class));
        assertTrue(testUser.isEnabled());
    }

    @Test
    void whenActivateAccount_withExpiredToken_thenThrowException() throws MessagingException {
        testToken.setExpiresAt(LocalDateTime.now().minusMinutes(1));
        when(tokenRepository.findByToken("123456")).thenReturn(Optional.of(testToken));

        assertThrows(RuntimeException.class,
                () -> authentifictionService.activateAccount("123456"));

        verify(emailService).sendEmail(anyString(), anyString(), any(), anyString(), anyString(), anyString());
    }
}
