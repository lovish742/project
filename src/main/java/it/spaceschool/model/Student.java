package it.spaceschool.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String username;
    private String fullName;
    private String email;
    private String password;

    private String birthDate; // simple string (e.g. "2000-01-01")
    private String motivationLetterPath;

    private final List<String> enrolledCourseIds = new ArrayList<>();

    public Student(String username, String fullName, String email, String password) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name cannot be empty");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be empty");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password cannot be empty");

        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }

    public String getFullName() { return fullName; }

    public String getEmail() { return email; }

    // Needed by persistence (DAOFile) to store and reload credentials
    public String getPassword() { return password; }

    public boolean passwordMatches(String raw) {
        return Objects.equals(password, raw);
    }

    public String getBirthDate() { return birthDate; }

    public String getMotivationLetterPath() { return motivationLetterPath; }

    public List<String> getEnrolledCourseIds() {
        return new ArrayList<>(enrolledCourseIds);
    }

    public void enroll(String courseId) {
        if (courseId == null || courseId.isBlank()) throw new IllegalArgumentException("Course id cannot be empty");
        if (!enrolledCourseIds.contains(courseId)) enrolledCourseIds.add(courseId);
    }

    // setters (useful for edit profile + register)
    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name cannot be empty");
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be empty");
        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password cannot be empty");
        this.password = password;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setMotivationLetterPath(String path) {
        this.motivationLetterPath = path;
    }
}
