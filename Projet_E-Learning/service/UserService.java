package service;

import model.*;
import dao.Database;
import utils.PasswordHasher;
import utils.Validator;
import java.util.UUID;

public class UserService {
    private Database db = Database.getInstance();

    public Student registerStudent(String username, String email, String password) {
        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email invalide");
        }
        if (!Validator.isValidPassword(password)) {
            throw new IllegalArgumentException("Mot de passe trop faible");
        }
        if (db.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Nom d'utilisateur déjà pris");
        }

        Student student = new Student(db.getTotalUsers() + 1, username, email, PasswordHasher.hash(password));
        db.saveUser(student);
        return student;
    }

    public Professor registerProfessor(String username, String email, String password) {
        Professor professor = new Professor(db.getTotalUsers() + 1, username, email, PasswordHasher.hash(password));
        db.saveUser(professor);
        return professor;
    }

    public String login(String username, String password) {
        User user = db.getUserByUsername(username);
        if (user != null && PasswordHasher.verify(password, user.getPassword())) {
            String sessionId = UUID.randomUUID().toString();
            db.createSession(sessionId, username);
            return sessionId;
        }
        return null;
    }

    public void logout(String sessionId) {
        db.removeSession(sessionId);
    }
}