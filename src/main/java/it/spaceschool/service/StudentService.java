package it.spaceschool.service;

import it.spaceschool.dao.StudentDAO;
import it.spaceschool.model.Student;

import java.util.Objects;

public class StudentService {

    private final StudentDAO studentDAO;
    private final EmailService emailService = new EmailService();

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Use case: Register
    public void register(Student student) {
        Objects.requireNonNull(student, "Student cannot be null");

        // basic required fields
        if (student.getBirthDate() == null || student.getBirthDate().isBlank()) {
            throw new IllegalArgumentException("Birth date is required");
        }

        // 1) Unique username
        if (studentDAO.findByUsername(student.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exists: " + student.getUsername());
        }

        // 2) Unique email
        if (studentDAO.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists: " + student.getEmail());
        }

        // 3) Motivation letter is mandatory
        String path = student.getMotivationLetterPath();
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Motivation letter is required");
        }

        studentDAO.save(student);
    }

    // Use case: Login
    public Student login(String username, String password) {
        Student student = studentDAO.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!student.passwordMatches(password)) {
            throw new IllegalArgumentException("Wrong password");
        }

        return student;
    }

    // Use case: Enroll (important for FULL version persistence)
    public void enrollCourse(Student student, String courseId) {
        student.enroll(courseId);
        studentDAO.update(student); // ✅ important for FULL_VERSION
        // send enrollment email (mock)
        emailService.sendCourseRegistrationEmail(student, courseId);
    }
}
