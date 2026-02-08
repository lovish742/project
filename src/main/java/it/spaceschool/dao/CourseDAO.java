package it.spaceschool.dao;

import it.spaceschool.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    List<Course> findAll();
    Optional<Course> findById(String id);
}
