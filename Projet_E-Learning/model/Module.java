package model;

import java.io.Serializable;
import java.util.*;

public class Module implements Serializable {
    private int id;
    private String title;
    private String description;
    private int order;
    private List<Lesson> lessons;

    public Module(int id, String title, String description, int order) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.order = order;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public List<Lesson> getLessons() { return lessons; }
}