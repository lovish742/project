package it.spaceschool.ui;


import it.spaceschool.util.LoggerUtil;
import it.spaceschool.model.Course;
import it.spaceschool.model.Student;
import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private final CourseService courseService;
    private final StudentService studentService;

    private Student loggedStudent = null;

    public ConsoleApp(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    // ========= ENTRY POINT FOR UI =========
    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            ConsolePrompter prompter = new ConsolePrompter(sc);
            while (true) {
                LoggerUtil.info("\n=== SPACE SCHOOL (Home) ===");
                LoggerUtil.info("1) View courses");
                LoggerUtil.info("2) Register");
                LoggerUtil.info("3) Login");
                LoggerUtil.info("0) Exit");
                String choice = prompter.promptRequired("> ");

                switch (choice) {
                    case "1" -> viewCourses();
                    case "2" -> registerFlow(prompter);
                    case "3" -> loginFlow(prompter);
                    case "0" -> {
                        LoggerUtil.info("Bye 👋");
                        return;
                    }
                    default -> LoggerUtil.info("Invalid choice. Try again.");
                }
            }
        }
    }

    // ========= FLOWS =========

    private void viewCourses() {
        List<Course> courses = courseService.getAllCourses();
        LoggerUtil.info("\n--- Available courses ---");

        if (courses.isEmpty()) {
            LoggerUtil.info("(no courses available)");
            return;
        }

        for (Course c : courses) {
            LoggerUtil.info(
                    c.getId() + " - " +
                            c.getName() + " (" +
                            c.getLevel() + ", " +
                            c.getDurationHours() + "h)"
            );
        }
    }

    private void loginFlow(ConsolePrompter prompter) {
        LoggerUtil.info("\n--- Login ---");

        String username = prompter.promptRequired("Username: ");
        String password = prompter.promptRaw("Password: ");

        try {
            loggedStudent = studentService.login(username, password);
            LoggerUtil.info("Welcome " + loggedStudent.getFullName() + " ✅");
        } catch (IllegalArgumentException ex) {
            LoggerUtil.info("Login failed: " + ex.getMessage());
            return;
        }

        profileMenu(prompter);
    }

    private void profileMenu(ConsolePrompter prompter) {
        if (loggedStudent == null) {
            LoggerUtil.info("No user logged in.");
            return;
        }

        while (true) {
            LoggerUtil.info("1) View profile");
            LoggerUtil.info("2) View enrolled courses");
            LoggerUtil.info("3) Enroll in a course");
            LoggerUtil.info("0) Logout");
            String choice = prompter.promptRequired("> ");

            switch (choice) {
                case "1" -> showProfile();
                case "2" -> showEnrolledCourses();
                case "3" -> enrollFlow(prompter);
                case "0" -> {
                    loggedStudent = null;
                    LoggerUtil.info("Logged out.");
                    return;
                }
                default -> LoggerUtil.info("Invalid choice.");
            }

        }

    }

    // ========= PROFILE ACTIONS =========

    private void showProfile() {
        if (loggedStudent == null) {
            LoggerUtil.info("No profile available.");
            return;
        }

        LoggerUtil.info("\n=== PROFILE MENU ===");
        LoggerUtil.info("Username: " + loggedStudent.getUsername());
        LoggerUtil.info("Full name: " + loggedStudent.getFullName());
        LoggerUtil.info("Email: " + loggedStudent.getEmail());
        LoggerUtil.info("Birth date: " + loggedStudent.getBirthDate());
        LoggerUtil.info("Motivation letter: " + loggedStudent.getMotivationLetterPath());
    }

    private void showEnrolledCourses() {
        LoggerUtil.info("\n--- Enrolled courses ---");

        if (loggedStudent == null) {
            LoggerUtil.info("(no student logged in)");
            return;
        }

        List<String> ids = loggedStudent.getEnrolledCourseIds();

        if (ids.isEmpty()) {
            LoggerUtil.info("(none)");
            return;
        }

        for (String id : ids) {
            courseService.findCourseById(id).ifPresentOrElse(
                    c -> LoggerUtil.info(c.getId() + " - " + c.getName() +
                            " (" + c.getLevel() + ", " + c.getDurationHours() + "h)"),
                    () -> LoggerUtil.info(id + " (course not found)")
            );
        }
    }



    private void enrollFlow(ConsolePrompter prompter) {
        if (loggedStudent == null) {
            LoggerUtil.info("Please login before enrolling.");
            return;
        }

        LoggerUtil.info("\n--- Enroll in a course ---");
        viewCourses();

        String courseId = prompter.promptRequired("Type course ID to enroll (e.g., ASTRO101): ");

        if (courseService.findCourseById(courseId).isEmpty()) {
            LoggerUtil.info("Course not found ❌");
            return;
        }

        studentService.enrollCourse(loggedStudent, courseId);
        LoggerUtil.info("Enrolled ✅");
    }

    private void registerFlow(ConsolePrompter prompter) {
        LoggerUtil.info("\n--- Registration ---");

        String username = prompter.promptRequired("Username: ");
        String fullName = prompter.promptRequired("Full name: ");
        String email = prompter.promptRequired("Email: ");
        String password = prompter.promptRaw("Password: ");
        String birthDate = prompter.promptRequired("Birth date (YYYY-MM-DD): ");
        String motivationPath = prompter.promptRequired("Motivation letter path: ");

        try {
            Student s = new Student(username, fullName, email, password);
            s.setBirthDate(birthDate);
            s.setMotivationLetterPath(motivationPath);

            studentService.register(s);
            LoggerUtil.info("Registration completed ✅");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            LoggerUtil.info("Registration failed: " + ex.getMessage());
        }
    }

}
