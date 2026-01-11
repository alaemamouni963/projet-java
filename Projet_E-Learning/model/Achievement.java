package model;

import java.io.Serializable;

public class Achievement implements Serializable {
    private String name;
    private String description;
    private int points;

    public Achievement(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPoints() { return points; }
}