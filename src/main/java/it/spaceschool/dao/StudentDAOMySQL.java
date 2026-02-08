package it.spaceschool.dao;

import it.spaceschool.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentDAOMySQL implements StudentDAO {

    @Override
    public void save(Student s) {
        String sql = "INSERT INTO students (username, full_name, email, password, birth_date, motivation_letter_path, enrolled_course_ids) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getUsername());
            ps.setString(2, s.getFullName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPassword());
            ps.setString(5, s.getBirthDate());
            ps.setString(6, s.getMotivationLetterPath());
            ps.setString(7, String.join(",", s.getEnrolledCourseIds()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving student", e);
        }
    }

    @Override
    public void update(Student s) {
        String sql = "UPDATE students SET full_name=?, email=?, password=?, birth_date=?, motivation_letter_path=?, enrolled_course_ids=? WHERE username=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getFullName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getBirthDate());
            ps.setString(5, s.getMotivationLetterPath());
            ps.setString(6, String.join(",", s.getEnrolledCourseIds()));
            ps.setString(7, s.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        String sql = "SELECT * FROM students WHERE username = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding student by username", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding student by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        String sql = "SELECT * FROM students";
        List<Student> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students", e);
        }
        return list;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student(
                rs.getString("username"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password")
        );
        s.setBirthDate(rs.getString("birth_date"));
        s.setMotivationLetterPath(rs.getString("motivation_letter_path"));
        String enrolled = rs.getString("enrolled_course_ids");
        if (enrolled != null && !enrolled.isBlank()) {
            for (String id : enrolled.split(",")) {
                if (!id.isBlank()) s.enroll(id.trim());
            }
        }
        return s;
    }
}
