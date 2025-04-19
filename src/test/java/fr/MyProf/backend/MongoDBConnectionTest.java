package fr.MyProf.backend;

import fr.myprof.backend.model.Role;
import fr.myprof.backend.model.User;
import fr.myprof.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MongoDBConnectionTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testMongoDBConnection() {
        // Vérifie que MongoTemplate est injecté
        assertNotNull(mongoTemplate, "MongoTemplate should be autowired");
        
        // Vérifie que la base de données est accessible
        String dbName = mongoTemplate.getDb().getName();
        assertNotNull(dbName, "Database name should not be null");
        assertTrue(dbName.endsWith("_test"), "Test database name should end with '_test'");
    }

    @Test
    public void testUserCreationAndRetrieval() {
        // Crée un utilisateur de test
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setRole(Role.STUDENT);
        testUser.setEnabled(true);

        // Sauvegarde l'utilisateur
        User savedUser = userRepository.save(testUser);
        assertNotNull(savedUser.getId(), "User ID should not be null after saving");

        // Récupère l'utilisateur par email
        User foundUser = userRepository.findByEmail("test@example.com")
                .orElse(null);
        assertNotNull(foundUser, "Should find user by email");
        assertEquals("Test", foundUser.getFirstName(), "First name should match");
        assertEquals(Role.STUDENT, foundUser.getRole(), "Role should match");
        assertTrue(passwordEncoder.matches("password123", foundUser.getPassword()), 
                "Password should be correctly encoded");

        // Nettoie la base de données de test
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testUserRepositoryOperations() {
        // Vérifie que le repository est injecté
        assertNotNull(userRepository, "UserRepository should be autowired");

        // Vérifie qu'il n'y a pas d'utilisateur avec cet email
        String testEmail = "nonexistent@example.com";
        assertFalse(userRepository.existsByEmail(testEmail), 
                "Should not find user with email: " + testEmail);

        // Vérifie que la recherche par email retourne empty pour un utilisateur non existant
        assertTrue(userRepository.findByEmail(testEmail).isEmpty(), 
                "Should return empty Optional for non-existent email");
    }
} 