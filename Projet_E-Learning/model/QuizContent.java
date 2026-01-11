package model;

import model.enums.ContentType;

public class QuizContent extends Content {
    private int numberOfQuestions;
    private double passingScore;

    public QuizContent(String title, String description, int duration) {
        super(title, description, ContentType.QUIZ, duration);
        this.numberOfQuestions = 10;
        this.passingScore = 70.0;
    }

    @Override
    public void display() {
        System.out.println("📝 Quiz: " + getTitle());
        System.out.println("❓ Nombre de questions: " + numberOfQuestions);
        System.out.println("🎯 Score minimum: " + passingScore + "%");
    }

    public int getNumberOfQuestions() { return numberOfQuestions; }
    public void setNumberOfQuestions(int numberOfQuestions) { this.numberOfQuestions = numberOfQuestions; }
    public double getPassingScore() { return passingScore; }
    public void setPassingScore(double passingScore) { this.passingScore = passingScore; }
}