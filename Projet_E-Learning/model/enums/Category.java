package model.enums;

public enum Category {
    PROGRAMMING("Programmation"),
    MATHEMATICS("Mathématiques"),
    SCIENCE("Sciences"),
    BUSINESS("Business"),
    ARTS("Arts"),
    LANGUAGES("Langues"),
    WEB("Développement Web"),
    DATABASE("Bases de données"),
    AI("Intelligence Artificielle");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}