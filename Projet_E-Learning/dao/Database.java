package dao;

import model.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static Database instance;
    private Map<Integer, User> users;
    private Map<Integer, Course> courses;
    private Map<Integer, Exam> exams;
    private Map<String, String> sessions;

    private Database() {
        users = new ConcurrentHashMap<>();
        courses = new ConcurrentHashMap<>();
        exams = new ConcurrentHashMap<>();
        sessions = new ConcurrentHashMap<>();
        chargerDonnees();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // === UTILISATEURS ===
    public void saveUser(User user) {
        users.put(user.getId(), user);
        sauvegarderDonnees();
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public User getUserByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User getUserBySession(String sessionId) {
        String username = sessions.get(sessionId);
        return (username != null) ? getUserByUsername(username) : null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void deleteUser(int userId) {
        users.remove(userId);
        sauvegarderDonnees();
    }

    // === SESSIONS ===
    public void createSession(String sessionId, String username) {
        sessions.put(sessionId, username);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    // === COURS ===
    public void saveCourse(Course course) {
        courses.put(course.getId(), course);
        sauvegarderDonnees();
    }

    public Course getCourseById(int id) {
        return courses.get(id);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    // === EXAMENS ===
    public void saveExam(Exam exam) {
        exams.put(exam.getId(), exam);
        sauvegarderDonnees();
    }

    public Exam getExamById(int id) {
        return exams.get(id);
    }

    public List<Exam> getAllExams() {
        return new ArrayList<>(exams.values());
    }

    // === PERSISTANCE ===
    @SuppressWarnings("unchecked")
    private void chargerDonnees() {
        File fichier = new File("elearning_data.dat");
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
                Map<String, Object> donnees = (Map<String, Object>) ois.readObject();
                users = (Map<Integer, User>) donnees.get("users");
                courses = (Map<Integer, Course>) donnees.get("courses");
                exams = (Map<Integer, Exam>) donnees.get("exams");
                sessions = (Map<String, String>) donnees.get("sessions");
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement: " + e.getMessage());
            }
        }
    }

    private void sauvegarderDonnees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("elearning_data.dat"))) {
            Map<String, Object> donnees = new HashMap<>();
            donnees.put("users", users);
            donnees.put("courses", courses);
            donnees.put("exams", exams);
            donnees.put("sessions", sessions);
            oos.writeObject(donnees);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde: " + e.getMessage());
        }
    }
    public Exam getExamByCourseId(int courseId) {
        for (Exam exam : exams.values()) {
            if (exam.getCourse().getId() == courseId) {
                return exam;
            }
        }
        return null;
    }

    // === STATISTIQUES ===
    public int getTotalUsers() {
        return users.size();
    }

    public int getStudentCount() {
        return (int) users.values().stream()
                .filter(u -> u instanceof Student)
                .count();
    }

    public int getProfessorCount() {
        return (int) users.values().stream()
                .filter(u -> u instanceof Professor)
                .count();
    }
}