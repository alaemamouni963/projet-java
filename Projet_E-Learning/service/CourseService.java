package service;

import model.*;
import model.enums.*;
import dao.Database;
import java.util.*;

public class CourseService {
    private Database db = Database.getInstance();

    public Course createCourse(String title, String description, Category category,
                               Level level, Professor professor) {
        Course course = new Course(db.getAllCourses().size() + 1, title, description, category, level, professor);
        professor.addCourse(course);
        db.saveCourse(course);
        db.saveUser(professor);
        return course;
    }

    public void enrollStudent(Student student, int courseId) {
        Course course = db.getCourseById(courseId);
        if (course != null && !student.isEnrolledInCourse(courseId)) {
            Enrollment enrollment = new Enrollment(student, course, new java.sql.Date(System.currentTimeMillis()).toLocalDate());
            student.addEnrollment(enrollment);
            course.addStudent(student);
            db.saveUser(student);
            db.saveCourse(course);
            System.out.println("✅ Étudiant inscrit au cours " + course.getTitle());
        }
    }

    public double getAverageCompletion(Course course) {
        if (course.getEnrolledStudents().isEmpty()) return 0.0;
        double total = 0.0;
        for (Student student : course.getEnrolledStudents()) {
            total += student.getCourseProgress(course.getId());
        }
        return total / course.getEnrolledStudents().size();
    }
}