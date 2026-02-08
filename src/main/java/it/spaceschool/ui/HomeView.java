package it.spaceschool.ui;

import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeView {

    private final AppShell shell;
    private final CourseService courseService;
    private final StudentService studentService;

    private final VBox root = new VBox(18);

    public HomeView(AppShell shell, CourseService courseService, StudentService studentService) {
        this.shell = shell;
        this.courseService = courseService;
        this.studentService = studentService;

        Label title = new Label("SPACE SCHOOL");
        title.getStyleClass().add("title");

        Label subtitle = new Label("for the first time in rome");
        subtitle.getStyleClass().add("subtitle");

        Button btnCourses = new Button("Corsi disponibili");
        Button btnRegister = new Button("Nuova registrazione");
        Button btnLogin = new Button("Login");

        btnCourses.getStyleClass().add("primary-btn");
        btnRegister.getStyleClass().add("primary-btn");
        btnLogin.getStyleClass().add("primary-btn");

        btnCourses.setOnAction(e ->
                shell.setContent(new CoursesView(shell, courseService, studentService).getRoot())
        );
        btnRegister.setOnAction(e ->
                shell.setContent(new RegisterView(shell, courseService, studentService).getRoot())
        );
        btnLogin.setOnAction(e ->
                shell.setContent(new LoginView(shell, courseService, studentService).getRoot())
        );

        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("glass-card");
        root.getChildren().addAll(title, subtitle, btnCourses, btnRegister, btnLogin);
    }

    public Parent getRoot() {
        return root;
    }
}
