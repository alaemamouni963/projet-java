package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Certificate implements Serializable {
    private String id;
    private Student student;
    private Course course;
    private double score;
    private LocalDate issueDate;

    public Certificate(Student student, Course course, double score) {
        this.id = "CERT-" + System.currentTimeMillis();
        this.student = student;
        this.course = course;
        this.score = score;
        this.issueDate = LocalDate.now();
    }

    public String generateCertificateText() {
        return "CERTIFICAT DE RÉUSSITE\n\n" +
                "Ce certificat atteste que\n" +
                student.getFullName() + "\n\n" +
                "a suivi avec succès le cours\n" +
                "\"" + course.getTitle() + "\"\n\n" +
                "avec un score de " + score + "%\n\n" +
                "Date d'émission: " + issueDate + "\n" +
                "Identifiant: " + id;
    }

    // Getters
    public LocalDate getIssueDate() {return issueDate;}
    public String getId() { return id; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getScore() { return score; }
}