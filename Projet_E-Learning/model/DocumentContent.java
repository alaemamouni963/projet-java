package model;

import model.enums.ContentType;

public class DocumentContent extends Content {

    public DocumentContent(String title, String description, int duration) {
        super(title, description, ContentType.DOCUMENT, duration);
    }

    @Override
    public void display() {
        System.out.println("📄 Affichage du document: " + title);
        System.out.println("📝 Description: " + description);
        System.out.println("⏱️ Temps estimé: " + duration + " minutes");
    }
}