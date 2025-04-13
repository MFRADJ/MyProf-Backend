package com.MyProf.MyProf_Backend.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String confirmPassword;

}
