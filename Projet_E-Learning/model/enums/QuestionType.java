package model.enums;

public enum QuestionType {
    MULTIPLE_CHOICE("QCM"),
    TRUE_FALSE("Vrai/Faux"),
    OPEN_QUESTION("Question ouverte"),
    MULTIPLE_RESPONSE("Réponses multiples");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}