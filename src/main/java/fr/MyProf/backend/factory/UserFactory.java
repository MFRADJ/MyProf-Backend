package fr.MyProf.backend.factory;

import fr.MyProf.backend.model.*;

public class UserFactory {
    
    public static User createUser(Role role) {
        return switch (role) {
            case TEACHER -> new Teacher();
            case STUDENT -> new Student();
            case PARENT -> new Parent();
            case ADMIN -> new Admin();
            default -> throw new IllegalArgumentException("Role non supporté: " + role);
        };
    }
} 