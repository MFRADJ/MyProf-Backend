package com.MyProf.MyProf_Backend.services;

import com.MyProf.MyProf_Backend.dto.AuthResponse;
import com.MyProf.MyProf_Backend.dto.LoginRequest;
import com.MyProf.MyProf_Backend.dto.SignupRequest;
import com.MyProf.MyProf_Backend.models.Profile;
import com.MyProf.MyProf_Backend.models.User;
import com.MyProf.MyProf_Backend.repositories.ProfileRepository;
import com.MyProf.MyProf_Backend.repositories.UserRepository;
import com.MyProf.MyProf_Backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Créer le nouvel utilisateur
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        user = userRepository.save(user);

        // Créer un profil vide pour l'utilisateur
        var profile = Profile.builder()
                .user(user)
                .build();
        profileRepository.save(profile);

        // Générer les tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return buildAuthResponse(user, jwtToken, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Récupérer l'utilisateur
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Générer les tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return buildAuthResponse(user, jwtToken, refreshToken);
    }

    private AuthResponse buildAuthResponse(User user, String jwtToken, String refreshToken) {
        // Vérifier si le profil est complété
        boolean profileCompleted = profileRepository.findByUser(user)
                .map(profile -> profile.getPhoneNumber() != null && !profile.getPhoneNumber().isEmpty())
                .orElse(false);

        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .profileCompleted(profileCompleted)
                .build();
    }
}
