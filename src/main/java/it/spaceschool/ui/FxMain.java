package it.spaceschool.ui;

import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.spaceschool.dao.DAOFactory;
import it.spaceschool.dao.DAOFactory;

public class FxMain extends Application {

    @Override
    public void start(Stage stage) {

        CourseService courseService = new CourseService(DAOFactory.createCourseDAO());
        StudentService studentService = new StudentService(DAOFactory.createStudentDAO());

        // بک گراند ثابت
        BackgroundView background = new BackgroundView(); // پایین کدشو میدم

        AppShell shell = new AppShell(background.getRoot());

        Scene scene = new Scene(shell.getRoot(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // استایل همیشه فعال

        // صفحه اول
        shell.setContent(new HomeView(shell, courseService, studentService).getRoot());

        stage.setTitle("SpaceSchool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
