package model;

import java.io.Serializable;
import java.util.*;

public class Lesson implements Serializable {
    private int id;
    private String title;
    private String description;
    private int duration;
    private List<Content> contents;

    public Lesson(int id, String title, String description, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.contents = new ArrayList<>();
    }

    public void addContent(Content content) {
        contents.add(content);
    }

    // Getters et setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getDuration() { return duration; }
    public List<Content> getContents() { return contents; }
}