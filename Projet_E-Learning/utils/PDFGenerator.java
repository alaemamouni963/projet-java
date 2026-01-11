package utils;

import model.Student;

public class PDFGenerator {

    public static void generateStudentProgressReport(Student student) {
        System.out.println("\n=== RAPPORT PDF ===");
        System.out.println("Étudiant: " + student.getFullName());
        System.out.println("Heures d'apprentissage: " + student.getTotalLearningHours());
        System.out.println("Cours complétés: " + student.getCompletedCoursesCount());
        System.out.println("Score moyen: " + student.getAverageGrade());
        System.out.println("==================");
        System.out.println("✅ Rapport PDF généré avec succès (simulation)");
    }
}