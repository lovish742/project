package it.spaceschool.ui;


import it.spaceschool.util.LoggerUtil;
import it.spaceschool.model.Course;
import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import java.util.List;

public class CoursesView {

    private final AppShell shell;
    private final CourseService courseService;
    private final StudentService studentService;

    private final VBox root = new VBox(18);

    public CoursesView(AppShell shell, CourseService courseService, StudentService studentService) {
        this.shell = shell;
        this.courseService = courseService;
        this.studentService = studentService;

        Label title = new Label("Corsi disponibili");
        title.getStyleClass().add("title");

        // ---------- جدول ----------
        VBox table = new VBox(12);
        table.getStyleClass().add("courses-card");

        GridPane header = new GridPane();
        header.getStyleClass().add("courses-header");
        header.setHgap(18);
        header.setPadding(new Insets(10, 16, 10, 16));

        Label h1 = new Label("corso");
        Label h2 = new Label("livello");
        Label h3 = new Label("durata");
        Label h4 = new Label("dettaghi");

        h1.getStyleClass().add("courses-header-text");
        h2.getStyleClass().add("courses-header-text");
        h3.getStyleClass().add("courses-header-text");
        h4.getStyleClass().add("courses-header-text");

        header.add(h1, 0, 0);
        header.add(h2, 1, 0);
        header.add(h3, 2, 0);
        header.add(h4, 3, 0);

        // ستون‌بندی
        ColumnConstraints c0 = new ColumnConstraints();
        c0.setHgrow(Priority.ALWAYS);
        c0.setMinWidth(260);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(120);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(120);

        ColumnConstraints c3 = new ColumnConstraints();
        c3.setMinWidth(140);

        header.getColumnConstraints().addAll(c0, c1, c2, c3);

        VBox rows = new VBox(10);
        rows.setPadding(new Insets(0, 0, 6, 0));

        List<Course> courses = courseService.getAllCourses();

        for (Course c : courses) {
            GridPane row = new GridPane();
            row.getStyleClass().add("course-row");
            row.setHgap(18);
            row.setPadding(new Insets(14, 16, 14, 16));
            row.getColumnConstraints().addAll(
                    copy(c0), copy(c1), copy(c2), copy(c3)
            );

            // ✅ اینجا اسم getter ها رو با مدل خودت هماهنگ کن:
            // اگر تو مدل Course متدها فرق دارن (مثلاً getId / getName / getDurationHours)، همین 4 خط رو عوض کن
            String code  = c.getId();
            String name  = c.getName();
            String level = c.getLevel();
            int hours    = c.getDurationHours();
            // 10 / 20

            Label lblName = new Label(name.toLowerCase());
            lblName.getStyleClass().add("course-name");

            Label lblLevel = new Label(level.toLowerCase());
            lblLevel.getStyleClass().add("course-pill");

            Label lblHours = new Label(hours + " h");
            lblHours.getStyleClass().add("course-hours");

            Hyperlink linkEnroll = new Hyperlink("dettaghi sul corso");
            linkEnroll.getStyleClass().add("course-link");

            // فعلاً برای دموی “لینک”، فقط پیام می‌زنیم
            // اگر بعداً صفحه enroll ساختی اینجا ببرش
            linkEnroll.setOnAction(e -> {
                // TODO: enroll flow
                LoggerUtil.info("Enroll clicked: " + code);
            });

            row.add(lblName, 0, 0);
            row.add(lblLevel, 1, 0);
            row.add(lblHours, 2, 0);
            row.add(linkEnroll, 3, 0);

            rows.getChildren().add(row);
        }

        table.getChildren().addAll(header, rows);

        Button btnBack = new Button("← Back");
        btnBack.getStyleClass().add("primary-btn");
        btnBack.setOnAction(e ->
                shell.setContent(new HomeView(shell, courseService, studentService).getRoot())
        );

        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("page-center");
        root.getChildren().addAll(title, table, btnBack);
    }

    public Parent getRoot() {
        return root;
    }

    private static ColumnConstraints copy(ColumnConstraints c) {
        ColumnConstraints x = new ColumnConstraints();
        x.setMinWidth(c.getMinWidth());
        x.setHgrow(c.getHgrow());
        return x;
    }
}
