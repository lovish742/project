package it.spaceschool.service;

import it.spaceschool.dao.CourseDAO;
import it.spaceschool.model.Course;
import java.util.Optional;
import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    // برای چک کردن وجود داشتن (بدون exception)
    public Optional<Course> findCourseById(String id) {
        return courseDAO.findById(id);
    }

    // برای جاهایی که باید حتما وجود داشته باشه (با exception)
    public Course getCourseByIdOrThrow(String id) {
        return courseDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }
}

