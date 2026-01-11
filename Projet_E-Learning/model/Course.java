package model;

import model.enums.Category;
import model.enums.Level;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Course implements Serializable {
    private int id;
    private String title;
    private String description;
    private Category category;
    private Level level;
    private Professor professor;
    private List<Module> modules;
    private List<Student> enrolledStudents;
    private LocalDate creationDate;
    private double price;
    private boolean isCertifiable;
    private double passingScore;

    public Course(int id, String title, String description, Category category,
                  Level level, Professor professor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.professor = professor;
        this.modules = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
        this.creationDate = LocalDate.now();
        this.price = 0.0;
        this.isCertifiable = true;
        this.passingScore = 70.0;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void addStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public List<Module> getModules() { return modules; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public boolean isCertifiable() { return isCertifiable; }
    public void setCertifiable(boolean certifiable) { isCertifiable = certifiable; }
    public double getPassingScore() { return passingScore; }
    public void setPassingScore(double passingScore) { this.passingScore = passingScore; }
}