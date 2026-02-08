package it.spaceschool.ui;

import it.spaceschool.model.Student;
import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {

    private final AppShell shell;
    private final CourseService courseService;
    private final StudentService studentService;

    private final VBox root = new VBox(12);

    public LoginView(AppShell shell, CourseService courseService, StudentService studentService) {
        this.shell = shell;
        this.courseService = courseService;
        this.studentService = studentService;

        Label title = new Label("Login");
        title.getStyleClass().add("title");

        TextField txtUser = new TextField();
        txtUser.setPromptText("Username");
        txtUser.getStyleClass().add("input");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Password");
        txtPass.getStyleClass().add("input");

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: white;");

        Button btnLogin = new Button("Login");
        btnLogin.getStyleClass().add("primary-btn");

        Button btnBack = new Button("← Back");
        btnBack.getStyleClass().add("primary-btn");

        btnBack.setOnAction(e ->
                shell.setContent(new HomeView(shell, courseService, studentService).getRoot())
        );

        btnLogin.setOnAction(e -> {
            try {
                Student logged = studentService.login(txtUser.getText().trim(), txtPass.getText());
                shell.setContent(new ProfileView(shell, courseService, studentService, logged).getRoot());
            } catch (Exception ex) {
                msg.setText(ex.getMessage());
            }
        });

        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("glass-card");
        root.getChildren().addAll(title, txtUser, txtPass, btnLogin, btnBack, msg);
    }

    public Parent getRoot() {
        return root;
    }
}
