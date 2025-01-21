package com.MyProf.MyProf_Backend.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir 10 chiffres")
    private String phoneNumber;
    
    @Size(max = 255, message = "L'adresse ne peut pas dépasser 255 caractères")
    private String address;
    
    @Size(max = 1000, message = "La bio ne peut pas dépasser 1000 caractères")
    private String bio;
    
    private String profilePictureUrl;
    
    // Champs spécifiques pour les enseignants
    private String subjects;
    private String educationLevel;
    
    // Champs spécifiques pour les étudiants
    private String grade;
    private String school;
    
    // Champs spécifiques pour les parents
    private String childrenInfo;
}
