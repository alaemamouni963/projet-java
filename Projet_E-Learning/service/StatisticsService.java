package service;

import model.*;
import dao.Database;
import java.util.*;

public class StatisticsService {
    private Database db = Database.getInstance();

    public int getTotalUsers() {
        return db.getTotalUsers();
    }

    public int getStudentCount() {
        return db.getStudentCount();
    }

    public int getProfessorCount() {
        return db.getProfessorCount();
    }

    public int getTotalCourses() {
        return db.getAllCourses().size();
    }

    public double getAverageCompletionRate() {
        double total = 0;
        int count = 0;
        for (Course course : db.getAllCourses()) {
            CourseService courseService = new CourseService();
            total += courseService.getAverageCompletion(course);
            count++;
        }
        return count > 0 ? total / count : 0;
    }
}