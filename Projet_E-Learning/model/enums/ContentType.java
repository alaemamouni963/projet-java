package model.enums;

public enum ContentType {
    VIDEO("Vidéo"),
    DOCUMENT("Document"),
    QUIZ("Quiz"),
    EXERCISE("Exercice"),
    LINK("Lien externe");

    private final String displayName;

    ContentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}