package patterns;

import model.*;
import model.enums.ContentType;

public class ContentFactory {

    public Content createContent(ContentType type, String title, String description, int duration) {
        return switch (type) {
            case VIDEO -> new VideoContent(title, description, duration);
            case DOCUMENT -> new DocumentContent(title, description, duration);
            case QUIZ -> {
                QuizContent quiz = new QuizContent(title, description, duration);
                quiz.setNumberOfQuestions(10);
                yield quiz;
            }
            default -> new DocumentContent(title, description, duration);
        };
    }
}

// Classe QuizContent manquante
class QuizContent extends model.Content {
    private int numberOfQuestions;

    public QuizContent(String title, String description, int duration) {
        super(title, description, model.enums.ContentType.QUIZ, duration);
    }

    @Override
    public void display() {
        System.out.println("📝 Quiz: " + getTitle());
        System.out.println("❓ Nombre de questions: " + numberOfQuestions);
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
