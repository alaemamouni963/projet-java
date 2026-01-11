package patterns;

import model.Question;
import java.util.List;

public interface GradingStrategy {
    double calculateScore(List<Question> questions, List<String> answers);
}

class StandardGrading implements GradingStrategy {
    @Override
    public double calculateScore(List<Question> questions, List<String> answers) {
        int correct = 0;
        for (int i = 0; i < Math.min(questions.size(), answers.size()); i++) {
            if (questions.get(i).isCorrect(answers.get(i))) {
                correct++;
            }
        }
        return (correct * 100.0) / questions.size();
    }
}

class NegativeMarkingGrading implements GradingStrategy {
    @Override
    public double calculateScore(List<Question> questions, List<String> answers) {
        int correct = 0;
        int incorrect = 0;
        for (int i = 0; i < Math.min(questions.size(), answers.size()); i++) {
            String answer = answers.get(i);
            if (answer != null && !answer.trim().isEmpty()) {
                if (questions.get(i).isCorrect(answer)) {
                    correct++;
                } else {
                    incorrect++;
                }
            }
        }
        double score = correct - (incorrect * 0.25);
        return Math.max(0, (score * 100) / questions.size());
    }
}