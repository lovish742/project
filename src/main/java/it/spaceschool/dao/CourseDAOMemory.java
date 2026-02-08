package it.spaceschool.dao;

import it.spaceschool.model.Course;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CourseDAOMemory implements CourseDAO {

    private final List<Course> courses = new ArrayList<>();

    public CourseDAOMemory() {
        courses.add(new Course("ASTRO101", "Astronomy 101", "base", 10));
        courses.add(new Course("ROCKET201", "Rocket Engineering", "adv", 20));
        courses.add(new Course("BIO301", "Space Biology", "adv", 20));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courses.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst();
    }
}
