package it.spaceschool.dao;

import it.spaceschool.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOMemory implements StudentDAO {

    private final List<Student> students = new ArrayList<>();

    @Override
    public void save(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        // جلوگیری از تکرار (upsert)
        update(s);
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        return students.stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public void update(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getUsername().equalsIgnoreCase(s.getUsername())) {
                students.set(i, s);
                return;
            }
        }

        // اگر وجود نداشت، اضافه کن
        students.add(s);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students); // defensive copy
    }
}
