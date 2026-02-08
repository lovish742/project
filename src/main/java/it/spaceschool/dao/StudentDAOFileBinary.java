package it.spaceschool.dao;

import it.spaceschool.model.Student;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOFileBinary implements StudentDAO {

    private static final String DEFAULT_FILE = "students.bin";
    private final Path filePath;

    public StudentDAOFileBinary() {
        this(Paths.get(DEFAULT_FILE));
    }

    public StudentDAOFileBinary(Path filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

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
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try {
            if (Files.size(filePath) == 0L) {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error checking students binary file size", e);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> list) {
                List<Student> students = new ArrayList<>();
                for (Object item : list) {
                    if (item instanceof Student student) {
                        students.add(student);
                    }
                }
                return students;
            }
            throw new RuntimeException("Invalid data in students binary file");
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading students binary file", e);
        }
    }

    private void write(List<Student> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException("Error writing students binary file", e);
        }
    }

    private void ensureFileExists() {
        try {
            File file = filePath.toFile();
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists() && !parent.mkdirs()) {
                    throw new IOException("Cannot create directory: " + parent);
                }
                if (!file.createNewFile()) {
                    throw new IOException("Cannot create file: " + filePath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create file: " + filePath, e);
        }
    }
}
