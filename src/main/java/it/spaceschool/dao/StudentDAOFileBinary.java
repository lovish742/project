package it.spaceschool.dao;

import it.spaceschool.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOFileBinary implements StudentDAO {

    private static final String FILE = "students.bin";

    @Override
    public void save(Student s) {
        List<Student> all = findAll();
        all.add(s);
        write(all);
    }

    @Override
    public void update(Student s) {
        List<Student> all = findAll();
        all.removeIf(st -> st.getUsername().equals(s.getUsername()));
        all.add(s);
        write(all);
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        return findAll().stream().filter(s -> s.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return findAll().stream().filter(s -> s.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public List<Student> findAll() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (List<Student>) obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading students binary file", e);
        }
    }

    private void write(List<Student> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException("Error writing students binary file", e);
        }
    }
}
