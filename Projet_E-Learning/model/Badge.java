package model;

import java.io.Serializable;

public class Badge implements Serializable {
    private String name;
    private String description;
    private String icon;

    public Badge(String name, String description) {
        this.name = name;
        this.description = description;
        this.icon = "🏆";
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
}