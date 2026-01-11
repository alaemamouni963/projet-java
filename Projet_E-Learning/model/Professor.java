package model;

import model.enums.Role;
import java.util.*;

public class Professor extends User {
    private String title;
    private String department;
    private List<Course> createdCourses;

    public Professor(int id, String username, String email, String password) {
        super(id, username, email, password, Role.PROFESSOR);
        this.createdCourses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        createdCourses.add(course);
    }

    // Getters et setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<Course> getCreatedCourses() { return createdCourses; }

    @Override
    public boolean hasPermission(String permission) {
        return switch (permission) {
            case "CREATE_COURSES", "MANAGE_COURSES", "CREATE_EXAMS",
                 "GRADE_EXAMS", "VIEW_STUDENT_STATS" -> true;
            default -> false;
        };
    }
}