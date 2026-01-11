package service;

import model.*;
import dao.Database;

public class NotificationService {
    private Database db = Database.getInstance();

    public void sendWelcomeNotification(User user) {
        Notification notification = new Notification("Bienvenue !",
                "Bienvenue sur la plateforme e-learning, " + user.getFirstName() + " !");
        user.addNotification(notification);
        db.saveUser(user);
    }

    public void sendCourseUpdateNotification(Course course, String message) {
        for (Student student : course.getEnrolledStudents()) {
            Notification notification = new Notification("Mise à jour du cours",
                    course.getTitle() + ": " + message);
            student.addNotification(notification);
            db.saveUser(student);
        }
    }

    public void sendExamResultNotification(Student student, Exam exam, double score) {
        String status = score >= exam.getPassingScore() ? "réussi" : "échoué";
        Notification notification = new Notification("Résultat d'examen",
                "Vous avez " + status + " l'examen " + exam.getTitle() + " avec " + score + "%");
        student.addNotification(notification);
        db.saveUser(student);
    }
}