package com.MyProf.MyProf_Backend.dto;

import com.MyProf.MyProf_Backend.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String refreshToken;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean profileCompleted;
}
