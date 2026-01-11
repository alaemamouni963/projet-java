package model;

import model.enums.ContentType;
import java.io.Serializable;

public abstract class Content implements Serializable {
    protected String title;
    protected String description;
    protected ContentType type;
    protected int duration;
    protected boolean isCompleted;

    public Content(String title, String description, ContentType type, int duration) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.duration = duration;
        this.isCompleted = false;
    }

    public void display() {
        System.out.println("Affichage du contenu: " + title);
        System.out.println("Type: " + type.getDisplayName());
        System.out.println("Durée: " + duration + " minutes");
    }

    // Getters et setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ContentType getType() { return type; }
    public int getDuration() { return duration; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}