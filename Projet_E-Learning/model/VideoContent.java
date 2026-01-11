package model;

import model.enums.ContentType;

public class VideoContent extends Content {

    public VideoContent(String title, String description, int duration) {
        super(title, description, ContentType.VIDEO, duration);
    }

    @Override
    public void display() {
        System.out.println("🎬 Lecture de la vidéo: " + title);
        System.out.println("📝 Description: " + description);
        System.out.println("⏱️ Durée: " + duration + " minutes");
    }
}