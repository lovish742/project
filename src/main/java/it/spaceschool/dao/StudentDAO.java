package it.spaceschool.dao;

import it.spaceschool.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    void save(Student s);
    void update(Student s);

    Optional<Student> findByUsername(String username);
    Optional<Student> findByEmail(String email);

    List<Student> findAll();
}
