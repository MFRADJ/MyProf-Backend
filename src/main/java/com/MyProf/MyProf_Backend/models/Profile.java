package com.MyProf.MyProf_Backend.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String phoneNumber;

    private String address;

    private String bio;

    private String profilePictureUrl;

    // Champs spécifiques pour les enseignants
    @Column(nullable = true)
    private String subjects; // Les matières enseignées, séparées par des virgules

    @Column(nullable = true)
    private String educationLevel; // Niveau d'éducation

    // Champs spécifiques pour les étudiants
    @Column(nullable = true)
    private String grade; // Niveau scolaire actuel

    @Column(nullable = true)
    private String school; // École actuelle

    // Champs spécifiques pour les parents
    @Column(nullable = true)
    private String childrenInfo; // Informations sur les enfants
}
