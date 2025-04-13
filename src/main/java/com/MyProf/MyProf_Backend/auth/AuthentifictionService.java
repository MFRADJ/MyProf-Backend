package com.MyProf.MyProf_Backend.auth;

import com.MyProf.MyProf_Backend.email.EmailService;
import com.MyProf.MyProf_Backend.email.EmailTemplateName;
import com.MyProf.MyProf_Backend.user.User;
import lombok.RequiredArgsConstructor;

import com.MyProf.MyProf_Backend.user.UserRepository;

import com.MyProf.MyProf_Backend.security.JwtService;
import com.MyProf.MyProf_Backend.roles.RoleRepository;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthentifictionService {

    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private  RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;


    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

//    public void register(RegistrationRequest request) throws MessagingException {
//
//        if (!request.getPassword().equals(request.getConfirmPassword())) {
//            throw new IllegalArgumentException("Passwords do not match!");
//        }
//        var userRole = roleRepository.findByName("USER")
//                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
//
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .accountLocked(false)
//                .enabled(true)
//                .roles(List.of(userRole))
//                .build();
//        userRepository.save(user);
//        sendValidationEmail(user);
//    }
    public void register(RegistrationRequest request) throws MessagingException {
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("Starting user registration...");

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> {
                    log.error("ROLE USER not found in the database!");
                    return new IllegalStateException("ROLE USER was not initiated");
                });

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();

        log.info("Saving user to the database...");
        userRepository.save(user);

        log.info("Sending validation email to user...");
        sendValidationEmail(user);

        log.info("User registration completed successfully.");
    }

//    public void register(RegistrationRequest request) throws MessagingException {
//        Logger logger = LoggerFactory.getLogger(getClass());
//
//        logger.info("Validation des mots de passe...");
//        if (!request.getPassword().equals(request.getConfirmPassword())) {
//            logger.error("Passwords do not match!");
//            throw new IllegalArgumentException("Passwords do not match!");
//        }
//
//        logger.info("Recherche du rôle ROLE_USER...");
//        var userRole = roleRepository.findByName("USER")
//                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
//
//        logger.info("Construction de l'utilisateur...");
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .accountLocked(false)
//                .enabled(true)
//                .roles(List.of(userRole))
//                .build();
//
//        logger.info("Enregistrement de l'utilisateur dans la base de données...");
//        userRepository.save(user);
//
//        logger.info("Envoi de l'email de validation...");
//        sendValidationEmail(user);
//
//        logger.info("Inscription terminée avec succès !");
//    }



    public AuthentificationResponse authenticate(AuthentificationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());


        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthentificationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(String.valueOf(savedToken.getUser().getId()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

//    public User promoteToProfessor(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Role professorRole = roleRepository.findByName("ROLE_PROFESSOR");
//        user.getRoles().add(professorRole);
//        return userRepository.save(user);
//    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
