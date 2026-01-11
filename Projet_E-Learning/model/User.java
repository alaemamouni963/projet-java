package model;

import model.enums.Role;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public abstract class User implements Serializable {
    protected int id;
    protected String username;
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected Role role;
    protected LocalDate registrationDate;
    protected boolean isActive;
    protected List<Notification> notifications;

    public User(int id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registrationDate = LocalDate.now();
        this.isActive = true;
        this.notifications = new ArrayList<>();
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDate getRegistrationDate() { return registrationDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public int getUnreadNotifications() {
        int count = 0;
        for (Notification n : notifications) {
            if (!n.isRead()) count++;
        }
        return count;
    }

    public abstract boolean hasPermission(String permission);
}