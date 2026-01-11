package model;

import model.enums.ContentType;

public class ExerciseContent extends Content {
    private String difficultyLevel;
    private boolean hasSolution;

    public ExerciseContent(String title, String description, int duration) {
        super(title, description, ContentType.EXERCISE, duration);
        this.difficultyLevel = "Moyen";
        this.hasSolution = true;
    }

    @Override
    public void display() {
        System.out.println("💪 Exercice: " + getTitle());
        System.out.println("📊 Niveau: " + difficultyLevel);
        System.out.println("✅ Solution incluse: " + (hasSolution ? "Oui" : "Non"));
    }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public boolean hasSolution() { return hasSolution; }
    public void setHasSolution(boolean hasSolution) { this.hasSolution = hasSolution; }
}