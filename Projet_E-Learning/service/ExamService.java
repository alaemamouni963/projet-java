package service;

import model.*;
import dao.Database;
import java.util.*;

public class ExamService {
    private Database db = Database.getInstance();

    public double corrigerExamen(List<Question> questions, List<String> reponses) {
        int correct = 0;
        for (int i = 0; i < Math.min(questions.size(), reponses.size()); i++) {
            if (questions.get(i).isCorrect(reponses.get(i))) {
                correct++;
            }
        }
        return (correct * 100.0) / questions.size();
    }

    public Exam creerExamen(String titre, Course cours, int duree) {
        Exam examen = new Exam(db.getAllExams().size() + 1, titre, cours, duree);
        db.saveExam(examen);
        return examen;
    }
}