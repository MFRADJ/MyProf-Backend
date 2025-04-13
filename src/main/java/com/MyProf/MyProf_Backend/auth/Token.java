// src/main/java/com/MyProf/MyProf_Backend/auth/Token.java
package com.MyProf.MyProf_Backend.auth;

import com.MyProf.MyProf_Backend.user.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tokens")
public class Token {

    @Id
    private String id;

    private String token;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;

    // Relation Many-to-One avec l'utilisateur via une référence MongoDB
    @DBRef
    private User user;
}
