//package com.MyProf.MyProf_Backend.auth;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.MyProf.MyProf_Backend.roles.Role;
//import com.MyProf.MyProf_Backend.security.JwtService;
//import com.MyProf.MyProf_Backend.user.User;
//import com.MyProf.MyProf_Backend.user.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@WebMvcTest(AuthentificationController.class)
//@AutoConfigureMockMvc(addFilters = false) // Désactive les filtres de sécurité
//@ImportAutoConfiguration(exclude = {
//        SecurityAutoConfiguration.class,
//        OAuth2ClientAutoConfiguration.class,
//        OAuth2ResourceServerAutoConfiguration.class
//})
//public class AuthentificationcontrollerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthentifictionService service;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    private final ObjectMapper objectMapper = new ObjectMapper(); // Utilise la version standard
////
////    @Test
////    public void testConnection_Success() throws Exception {
////        when(userRepository.count()).thenReturn(5L);
////
////        mockMvc.perform(get("/auth/test-connection"))
////                .andExpect(status().isOk())
////                .andExpect(content().string(containsString("Connected to myprofdb! Users count: 5")));
////    }
////
////    @Test
////    public void testConnection_Failure() throws Exception {
////        when(userRepository.count()).thenThrow(new RuntimeException("Database error"));
////
////        mockMvc.perform(get("/auth/test-connection"))
////                .andExpect(status().isInternalServerError())
////                .andExpect(content().string(containsString("Connection to myprofdb failed: Database error")));
////    }
//
//    @Test
//    public void register_Success() throws Exception {
//        RegistrationRequest request = RegistrationRequest.builder()
//                .email("test@example.com")
//                .password("password")
//                .confirmPassword("password")
//                .build();
//
//        doNothing().when(service).register(any(RegistrationRequest.class));
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isAccepted());
//    }
//
////    @Test
////    public void register_PasswordsDoNotMatch() throws Exception {
////        RegistrationRequest request = RegistrationRequest.builder()
////                .email("test@example.com")
////                .password("password")
////                .confirmPassword("differentPassword")
////                .build();
////
////        mockMvc.perform(post("/auth/register")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(request)))
////                .andExpect(status().isBadRequest())
////                .andExpect(content().string(containsString("Passwords do not match!")));
////    }
////
////    @Test
////    public void authenticate_Success() throws Exception {
////        AuthentificationRequest request = new AuthentificationRequest("test@example.com", "password");
////
////        AuthentificationResponse response = new AuthentificationResponse("token");
////
////        when(service.authenticate(any(AuthentificationRequest.class))).thenReturn(response);
////
////        mockMvc.perform(post("/auth/authenticate")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(request)))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.token").value("token"));
////    }
////
////    @Test
////    public void getCurrentUser_AccessGranted() throws Exception {
////        User user = new User();
////        user.setRoles(Collections.singletonList(Role.builder()
////                .name("ROLE_ADMIN")
////                .build()
////        ));
////
////        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
////
////        mockMvc.perform(get("/auth/user")
////                        .principal(() -> "test@example.com"))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
////    }
////
////    @Test
////    public void getCurrentUser_AccessDenied() throws Exception {
////        User user = new User();
////        user.setRoles(Collections.emptyList());
////
////        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
////
////        mockMvc.perform(get("/auth/user")
////                        .principal(() -> "test@example.com"))
////                .andExpect(status().isForbidden())
////                .andExpect(content().string("Access denied"));
////    }
//}
// src/test/java/com/MyProf/MyProf_Backend/auth/AuthentificationControllerTest.java
package com.MyProf.MyProf_Backend.auth;

import com.MyProf.MyProf_Backend.roles.Role;
import com.MyProf.MyProf_Backend.user.User;
import com.MyProf.MyProf_Backend.user.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthentificationControllerTest {

    @Mock
    private AuthentifictionService service;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthentificationController controller;

    public AuthentificationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConnection_Success() {
        when(userRepository.count()).thenReturn(10L);

        ResponseEntity<String> response = controller.testConnection();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Users count: 10"));
    }

    @Test
    void testConnection_Failure() {
        when(userRepository.count()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = controller.testConnection();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Connection to myprofdb failed"));
    }

    @Test
    void register_Success() throws MessagingException {
        RegistrationRequest request = new RegistrationRequest();
        doNothing().when(service).register(request);

        ResponseEntity<?> response = controller.register(request);

        assertEquals(202, response.getStatusCodeValue());
    }

    @Test
    void register_InvalidArgument() throws MessagingException {
        RegistrationRequest request = new RegistrationRequest();
        doThrow(new IllegalArgumentException("Invalid data")).when(service).register(request);

        ResponseEntity<?> response = controller.register(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid data", response.getBody());
    }

    @Test
    void register_EmailError() throws MessagingException {
        RegistrationRequest request = new RegistrationRequest();
        doThrow(new MessagingException()).when(service).register(request);

        ResponseEntity<?> response = controller.register(request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error sending validation email", response.getBody());
    }

    @Test
    void authenticate_Success() {
        // Build an authentication request
        AuthentificationRequest request = new AuthentificationRequest("test@example.com", "password");

        AuthentificationResponse expectedResponse = new AuthentificationResponse("token");
        when(service.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthentificationResponse> response = controller.authenticate(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void confirm_Success() throws MessagingException {
        String token = "validToken";
        doNothing().when(service).activateAccount(token);

        assertDoesNotThrow(() -> controller.confirm(token));
        verify(service).activateAccount(token);
    }

    @Test
    void getCurrentUser_AdminAccess() {
        // Mock user details
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("admin@test.com");

        // Build the mock user with roles (as a list for MongoDB)
        User mockUser = User.builder()
                .email("admin@test.com")
                .roles(List.of(  Role.builder()
                        .name("ROLE_ADMIN") // Use builder for Role
                        .build())) // Use List instead of Set for MongoDB
                .build();

        // Mock the repository behavior
        when(userRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(mockUser));

        // Call the controller method
        ResponseEntity<?> response = controller.getCurrentUser(userDetails);

        // Validate the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void getCurrentUser_ForbiddenAccess() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user@test.com");
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getCurrentUser(userDetails);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Access denied", response.getBody());
    }
}
