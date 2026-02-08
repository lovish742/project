package it.spaceschool;

import it.spaceschool.dao.CourseDAO;
import it.spaceschool.dao.CourseDAOMemory;
import it.spaceschool.dao.StudentDAO;
import it.spaceschool.dao.StudentDAOMemory;
import it.spaceschool.service.CourseService;
import it.spaceschool.service.StudentService;
import it.spaceschool.ui.ConsoleApp;

public class Main {
    public static void main(String[] args) {

        CourseDAO courseDAO = new CourseDAOMemory();
        StudentDAO studentDAO = new StudentDAOMemory();

        CourseService courseService = new CourseService(courseDAO);
        StudentService studentService = new StudentService(studentDAO);

        ConsoleApp app = new ConsoleApp(courseService, studentService);
        app.start();
    }
}
