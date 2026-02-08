package it.spaceschool.dao;

import it.spaceschool.model.Student;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

//این باعث میشه بعد از بستن برنامه هم داده‌ها بمونن.
public class StudentDAOFile implements StudentDAO {

    private final Path filePath;

    public StudentDAOFile(String filename) {
        this.filePath = Paths.get(filename);
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath,
                        "username,fullName,email,password,birthDate,motivationLetterPath\n",
                        StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create file: " + filePath, e);
        }
    }

    @Override
    public List<Student> findAll() {
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            List<String> lines = br.lines().collect(Collectors.toList());
            if (lines.isEmpty()) return new ArrayList<>();

            return lines.stream()
                    .skip(1)
                    .filter(l -> !l.isBlank())
                    .map(this::fromCsv)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + filePath, e);
        }
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        return findAll().stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return findAll().stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public void save(Student student) {
        List<Student> all = findAll();
        all.removeIf(s -> s.getUsername().equalsIgnoreCase(student.getUsername()));
        all.add(student);
        writeAll(all);
    }

    private void writeAll(List<Student> students) {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.write("username,fullName,email,password,birthDate,motivationLetterPath\n");
            for (Student s : students) {
                bw.write(toCsv(s));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + filePath, e);
        }
    }

    private String toCsv(Student s) {
        return String.join(",",
                esc(s.getUsername()),
                esc(s.getFullName()),
                esc(s.getEmail()),
                esc(s.getPassword()), // باید Student.getPassword() داشته باشی
                esc(nullToEmpty(s.getBirthDate())),
                esc(nullToEmpty(s.getMotivationLetterPath()))
        );
    }

    private Student fromCsv(String line) {
        List<String> p = parseCsv(line);
        while (p.size() < 6) p.add("");

        Student s = new Student(p.get(0), p.get(1), p.get(2), p.get(3));
        if (!p.get(4).isBlank()) s.setBirthDate(p.get(4));
        if (!p.get(5).isBlank()) s.setMotivationLetterPath(p.get(5));
        return s;
    }
    @Override
    public void update(Student student) {
        // در File-DAO، update عملاً همون save هست (upsert)
        save(student);
    }


    private String nullToEmpty(String v) {
        return v == null ? "" : v;
    }

    private String esc(String s) {
        if (s == null) return "\"\"";
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    private List<String> parseCsv(String line) {
        List<String> res = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                res.add(trimQuotes(cur.toString()));
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        res.add(trimQuotes(cur.toString()));
        return res;
    }

    private String trimQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
