package com.MyProf.MyProf_Backend.roles;

import com.MyProf.MyProf_Backend.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "roles")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Role {

    @Id
    private String id;

    private String name;

    // Utilisation d'une référence pour la relation Many-to-Many
    @DBRef
    private Set<User> users;

    // Méthodes supplémentaires (décommenter si nécessaire)
    // public Role(String name) {
    //     this.name = name;
    // }
    //
    // public void removeAllUsersFromRole() {
    //     if (this.getUsers() != null) {
    //         List<User> usersInRole = this.getUsers().stream().toList();
    //         usersInRole.forEach(this::removeUserFromRole);
    //     }
    // }
    //
    // public void removeUserFromRole(User user) {
    //     user.getRoles().remove(this);
    //     this.getUsers().remove(user);
    // }
    //
    // public void assignUserToRole(User user) {
    //     user.getRoles().add(this);
    //     this.getUsers().add(user);
    // }
}
