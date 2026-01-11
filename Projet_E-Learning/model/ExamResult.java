package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ExamResult implements Serializable {
    private Exam exam;
    private Student student;
    private List<String> answers;
    private double score;
    private boolean passed;
    private LocalDateTime submissionDate;

    public ExamResult(Exam exam, Student student, List<String> answers, double score, boolean passed) {
        this.exam = exam;
        this.student = student;
        this.answers = answers;
        this.score = score;
        this.passed = passed;
        this.submissionDate = LocalDateTime.now();
    }

    // Getters
    public Exam getExam() { return exam; }
    public Student getStudent() { return student; }
    public double getScore() { return score; }
    public boolean isPassed() { return passed; }
}