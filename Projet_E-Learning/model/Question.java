package model;

import model.enums.QuestionType;
import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String id;
    private String text;
    private List<String> options;
    private String correctAnswer;
    private QuestionType type;
    private double points;

    public Question(QuestionType type, String text, List<String> options, String correctAnswer, double points) {
        this.id = "Q" + System.currentTimeMillis();
        this.type = type;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    // Getters
    public String getId() { return id; }
    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public QuestionType getType() { return type; }
    public double getPoints() { return points; }
}