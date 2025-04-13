package com.MyProf.MyProf_Backend.auth;


import com.MyProf.MyProf_Backend.user.User;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.MyProf.MyProf_Backend.user.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor

public class AuthentificationController {

    private final AuthentifictionService service;
    private final UserRepository userRepository;

    @GetMapping("/test-connection")
    public ResponseEntity<String> testConnection() {
        try {
            // Essaie de compter les documents dans la collection "users"
            long count = userRepository.count();
            return ResponseEntity.ok("Connected to myprofdb! Users count: " + count);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Connection to myprofdb failed: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request)  {

        try {
            service.register(request);
            return ResponseEntity.accepted().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending validation email");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }

//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) {
//        try {
//            service.register(request);
//            return ResponseEntity.accepted().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(400).body(e.getMessage());
//        } catch (IllegalStateException e) {
//            return ResponseEntity.status(500).body(e.getMessage());
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.status(400).body("Email already exists");
//        } catch (MessagingException e) {
//            return ResponseEntity.status(500).body("Error sending validation email");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("An error occurred during registration");
//        }
//    }


    @PostMapping("/authenticate")
            public ResponseEntity<AuthentificationResponse> authenticate(@RequestBody AuthentificationRequest request) {
                return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/activate-account")
            public void confirm(@RequestParam String token) throws MessagingException {
                service.activateAccount(token);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        if (user.isPresent() && user.get().getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }

}