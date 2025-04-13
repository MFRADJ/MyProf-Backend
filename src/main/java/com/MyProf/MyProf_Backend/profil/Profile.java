// src/main/java/com/MyProf/MyProf_Backend/profil/Profile.java
package com.MyProf.MyProf_Backend.profil;

import com.MyProf.MyProf_Backend.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "profiles")
@Data
public class Profile {

    @Id
    private String id;

    private String address;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    // Autres champs de profil...

    // Référence à l'utilisateur, puisque MongoDB ne supporte pas directement les relations OneToOne
    @DBRef
    private User user;
}
