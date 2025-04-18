package fr.MyProf.backend.model;

import fr.MyProf.backend.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Admin extends User {
    public Admin() {
        super();
        this.setRole(Role.ADMIN);
    }
} 