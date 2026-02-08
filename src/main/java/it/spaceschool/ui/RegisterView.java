package it.spaceschool.ui;

import it.spaceschool.model.Student;
import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class RegisterView {

    private final AppShell shell;
    private final CourseService courseService;
    private final StudentService studentService;

    private final VBox root = new VBox(14);

    // مسیر فایل انتخاب‌شده
    private String motivationPath = null;

    public RegisterView(AppShell shell, CourseService courseService, StudentService studentService) {
        this.shell = shell;
        this.courseService = courseService;
        this.studentService = studentService;

        Label title = new Label("Registrazione");
        title.getStyleClass().add("title");

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.getStyleClass().add("input");

        TextField txtFullName = new TextField();
        txtFullName.setPromptText("Full name");
        txtFullName.getStyleClass().add("input");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("input");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.getStyleClass().add("input");

        TextField txtBirthDate = new TextField();
        txtBirthDate.setPromptText("Birth date (YYYY-MM-DD)");
        txtBirthDate.getStyleClass().add("input");

        // Upload motivation letter
        Button btnUpload = new Button("Upload motivation letter (PDF)");
        btnUpload.getStyleClass().add("secondary-btn");

        Label lblFile = new Label("No file selected");
        lblFile.getStyleClass().add("hint");

        Button btnSubmit = new Button("Submit");
        btnSubmit.getStyleClass().add("primary-btn");

        Button btnBack = new Button("← Back");
        btnBack.getStyleClass().add("primary-btn");

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: white; -fx-font-size: 14px;"); // ✅ دیده بشه

        btnUpload.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select motivation letter (PDF)");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF files", "*.pdf")
            );

            File file = chooser.showOpenDialog(null);
            if (file != null) {
                motivationPath = file.getAbsolutePath();
                lblFile.setText("Selected: " + file.getName());
                msg.setText(""); // اگر قبلاً خطا بوده پاک کن
            }
        });

        btnBack.setOnAction(e ->
                shell.setContent(new HomeView(shell, courseService, studentService).getRoot())
        );

        btnSubmit.setOnAction(e -> {
            msg.setText("");

            try {
                String username = txtUsername.getText().trim();
                String fullName = txtFullName.getText().trim();
                String email = txtEmail.getText().trim();
                String password = txtPassword.getText().trim();
                String birthDate = txtBirthDate.getText().trim();

                // ✅ Validation فیلدها
                if (username.isBlank() || fullName.isBlank() || email.isBlank()
                        || password.isBlank() || birthDate.isBlank()) {
                    msg.setText("❌ Please fill in all fields.");
                    return;
                }

                // ✅ Validation فایل
                if (motivationPath == null || motivationPath.isBlank()) {
                    msg.setText("❌ Please upload the motivation letter (PDF).");
                    return;
                }

                Student s = new Student(username, fullName, email, password);
                s.setBirthDate(birthDate);
                s.setMotivationLetterPath(motivationPath);

                studentService.register(s);

                msg.setText("✅ User registered successfully!");

                // (اختیاری) پاک کردن فرم بعد ثبت
                // txtUsername.clear(); txtFullName.clear(); txtEmail.clear(); txtPassword.clear(); txtBirthDate.clear();
                // motivationPath = null;
                // lblFile.setText("No file selected");

            } catch (Exception ex) {
                msg.setText("❌ " + ex.getMessage());
            }
        });

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
                title,
                txtUsername,
                txtFullName,
                txtEmail,
                txtPassword,
                txtBirthDate,
                btnUpload,
                lblFile,
                btnSubmit,
                btnBack,
                msg
        );
    }

    public Parent getRoot() {
        return root;
    }
}
