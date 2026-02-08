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
        Scanner sc = new Scanner(System.in);

        while (true) {
            LoggerUtil.info("\n=== SPACE SCHOOL (Home) ===");
            LoggerUtil.info("1) View courses");
            LoggerUtil.info("2) Register");
            LoggerUtil.info("3) Login");
            LoggerUtil.info("0) Exit");
            LoggerUtil.info("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> viewCourses();
                case "2" -> registerFlow(sc);
                case "3" -> loginFlow(sc);
                case "0" -> {
                    LoggerUtil.info("Bye 👋");
                    return;
                }
                default -> LoggerUtil.info("Invalid choice. Try again.");
            }
        }
    }

    // ========= FLOWS =========

    private void viewCourses() {
        List<Course> courses = courseService.getAllCourses();
        LoggerUtil.info("\n--- Available courses ---");

        for (Course c : courses) {
            LoggerUtil.info(
                    c.getId() + " - " +
                            c.getName() + " (" +
                            c.getLevel() + ", " +
                            c.getDurationHours() + "h)"
            );
        }
    }

    private void registerFlow(Scanner sc) {
        LoggerUtil.info("\n--- Registration ---");

        LoggerUtil.info("Username: ");
        String username = sc.nextLine().trim();

        LoggerUtil.info("Full name: ");
        String fullName = sc.nextLine().trim();

        LoggerUtil.info("Email: ");
        String email = sc.nextLine().trim();

        LoggerUtil.info("Password: ");
        String password = sc.nextLine();

        LoggerUtil.info("Birth date (YYYY-MM-DD): ");
        String birthDate = sc.nextLine().trim();

        LoggerUtil.info("Motivation letter path: ");
        String motivationPath = sc.nextLine().trim();

        Student s = new Student(username, fullName, email, password);
        s.setBirthDate(birthDate);
        s.setMotivationLetterPath(motivationPath);

        studentService.register(s);
        LoggerUtil.info("Registration completed ✅");
    }

    private void loginFlow(Scanner sc) {
        LoggerUtil.info("\n--- Login ---");

        LoggerUtil.info("Username: ");
        String username = sc.nextLine().trim();

        LoggerUtil.info("Password: ");
        String password = sc.nextLine();

        loggedStudent = studentService.login(username, password);
        LoggerUtil.info("Welcome " + loggedStudent.getFullName() + " ✅");

        profileMenu(sc);
    }

    private void profileMenu(Scanner sc) {
        while (true) {
            LoggerUtil.info("1) View profile");
            LoggerUtil.info("2) View enrolled courses");
            LoggerUtil.info("3) Enroll in a course");
            LoggerUtil.info("0) Logout");

            LoggerUtil.info("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> showProfile();
                case "2" -> showEnrolledCourses();
                case "3" -> enrollFlow(sc);
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
        LoggerUtil.info("\n=== PROFILE MENU ===");
        LoggerUtil.info("Username: " + loggedStudent.getUsername());
        LoggerUtil.info("Full name: " + loggedStudent.getFullName());
        LoggerUtil.info("Email: " + loggedStudent.getEmail());
        LoggerUtil.info("Birth date: " + loggedStudent.getBirthDate());
        LoggerUtil.info("Motivation letter: " + loggedStudent.getMotivationLetterPath());
    }

    private void showEnrolledCourses() {
        LoggerUtil.info("\n--- Enrolled courses ---");

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



    private void enrollFlow(Scanner sc) {
        LoggerUtil.info("\n--- Enroll in a course ---");
        viewCourses();

        LoggerUtil.info("Type course ID to enroll (e.g., ASTRO101): ");
        String courseId = sc.nextLine().trim();

        if (courseService.findCourseById(courseId).isEmpty()) {
            LoggerUtil.info("Course not found ❌");
            return;
        }

        studentService.enrollCourse(loggedStudent, courseId);
        LoggerUtil.info("Enrolled ✅");
    }


}
