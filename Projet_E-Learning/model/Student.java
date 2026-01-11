package model;

import model.enums.Role;
import java.util.*;

public class Student extends User {
    private List<Enrollment> enrollments;
    private Map<Integer, Double> courseProgress;
    private List<Certificate> certificates;
    private List<Badge> badges;
    private List<Achievement> achievements;
    private double totalLearningHours;
    private Map<Integer, List<ExamResult>> examResults;
    private String bio;

    public Student(int id, String username, String email, String password) {
        super(id, username, email, password, Role.STUDENT);
        this.enrollments = new ArrayList<>();
        this.courseProgress = new HashMap<>();
        this.certificates = new ArrayList<>();
        this.badges = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.totalLearningHours = 0;
        this.examResults = new HashMap<>();
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        courseProgress.put(enrollment.getCourse().getId(), 0.0);
    }

    public void updateCourseProgress(int courseId, double increment) {
        Double current = courseProgress.get(courseId);
        if (current != null) {
            double newProgress = Math.min(100.0, current + increment);
            courseProgress.put(courseId, newProgress);
        }
    }

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
    }

    public void addLearningTime(double hours) {
        totalLearningHours += hours;
    }

    public void addExamResult(int courseId, ExamResult result) {
        examResults.computeIfAbsent(courseId, k -> new ArrayList<>()).add(result);
    }

    // Getters
    public List<Enrollment> getEnrollments() { return enrollments; }
    public double getCourseProgress(int courseId) { return courseProgress.getOrDefault(courseId, 0.0); }
    public List<Certificate> getCertificates() { return certificates; }
    public List<Badge> getBadges() { return badges; }
    public List<Achievement> getAchievements() { return achievements; }
    public double getTotalLearningHours() { return totalLearningHours; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public int getCompletedCoursesCount() {
        return (int) courseProgress.values().stream()
                .filter(p -> p >= 100.0)
                .count();
    }

    public double getAverageGrade() {
        double total = 0;
        int count = 0;
        for (List<ExamResult> results : examResults.values()) {
            for (ExamResult result : results) {
                total += result.getScore();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    public boolean isEnrolledInCourse(int courseId) {
        return enrollments.stream()
                .anyMatch(e -> e.getCourse().getId() == courseId);
    }

    @Override
    public boolean hasPermission(String permission) {
        return switch (permission) {
            case "VIEW_COURSES", "ENROLL_COURSES", "TAKE_EXAMS", "VIEW_GRADES", "ACCESS_FORUM" -> true;
            default -> false;
        };
    }
}