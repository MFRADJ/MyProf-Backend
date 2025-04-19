package fr.MyProf.backend.users.models;

import fr.MyProf.backend.users.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Student extends User {
    public Student() {
        super();
        this.setRole(Role.STUDENT);
    }
} 