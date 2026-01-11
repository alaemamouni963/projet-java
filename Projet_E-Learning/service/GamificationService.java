package service;

import model.*;
import dao.Database;
import java.util.List;

public class GamificationService {
    private Database db = Database.getInstance();

    public void checkCourseProgressAchievements(Student student) {
        int completedCourses = student.getCompletedCoursesCount();

        // Badge pour le premier cours terminé
        if (completedCourses == 1 && student.getBadges().size() == 0) {
            Badge badge = new Badge("Premier pas", "A terminé son premier cours");
            student.addBadge(badge);
            db.saveUser(student);
        }

        // Badge pour 5 cours terminés
        if (completedCourses == 5) {
            Badge badge = new Badge("Apprenant assidu", "A terminé 5 cours");
            student.addBadge(badge);
            db.saveUser(student);
        }

        // Badge pour 50 heures d'apprentissage
        if (student.getTotalLearningHours() >= 50) {
            Badge badge = new Badge("Dévoué", "50 heures d'apprentissage");
            student.addBadge(badge);
            db.saveUser(student);
        }
    }

    public void awardCertificateBadge(Student student) {
        Badge badge = new Badge("Certifié", "A obtenu une certification");
        student.addBadge(badge);
        db.saveUser(student);
    }

    public void checkForumParticipationAchievements(Student student, int postCount) {
        if (postCount >= 10) {
            Badge badge = new Badge("Actif sur le forum", "A participé activement aux discussions");
            student.addBadge(badge);
            db.saveUser(student);
        }
    }
}