package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Exam implements Serializable {
    private int id;
    private String title;
    private Course course;
    private List<Question> questions;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int duration;
    private double passingScore;

    public Exam(int id, String title, Course course, int duration) {
        this.id = id;
        this.title = title;
        this.course = course;
        this.questions = new ArrayList<>();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(7);
        this.duration = duration;
        this.passingScore = 70.0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Getters et setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public Course getCourse() { return course; }
    public List<Question> getQuestions() { return questions; }
    public double getPassingScore() { return passingScore; }
    public void setPassingScore(double passingScore) { this.passingScore = passingScore; }
}