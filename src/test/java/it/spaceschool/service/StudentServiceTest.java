// Author: [REPLACE_WITH_STUDENT_NAME]
// NOTE: Please change the above line to your full name before submission.

package it.spaceschool.service;

import it.spaceschool.dao.StudentDAO;
import it.spaceschool.dao.StudentDAOMemory;
import it.spaceschool.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    void register_then_login_success() {
        // Arrange
        StudentDAO studentDAO = new StudentDAOMemory();
        StudentService studentService = new StudentService(studentDAO);

        Student s = new Student("ali", "Ali Space", "ali@mail.com", "1234");
        s.setBirthDate("2000-01-01");
        s.setMotivationLetterPath("motivation.pdf"); // هر چیزی، فقط خالی نباشه

        // Act
        studentService.register(s);
        Student logged = studentService.login("ali", "1234");

        // Assert
        assertNotNull(logged);
        assertEquals("ali", logged.getUsername());
        assertEquals("Ali Space", logged.getFullName());
    }

    @Test
    void login_wrong_password_throws() {
        // Arrange
        StudentDAO studentDAO = new StudentDAOMemory();
        StudentService studentService = new StudentService(studentDAO);

        Student s = new Student("ali", "Ali Space", "ali@mail.com", "1234");
        s.setBirthDate("2000-01-01");
        s.setMotivationLetterPath("motivation.pdf");
        studentService.register(s);

        // Act + Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> studentService.login("ali", "WRONG")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("wrong"));
    }

    @Test
    void login_user_not_found_throws() {
        // Arrange
        StudentDAO studentDAO = new StudentDAOMemory();
        StudentService studentService = new StudentService(studentDAO);

        // Act + Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> studentService.login("notExists", "1234")
        );

        // اگر پیام دقیق یادت نیست، فقط کلیدواژه رو چک کن
        assertTrue(ex.getMessage().toLowerCase().contains("not found"));
    }
    @Test
    void register_duplicate_email_throws() {
        StudentDAO studentDAO = new StudentDAOMemory();
        StudentService studentService = new StudentService(studentDAO);

        Student s1 = new Student("ali", "Ali Space", "ali@mail.com", "1234");
        s1.setBirthDate("2000-01-01");
        s1.setMotivationLetterPath("motivation.pdf");
        studentService.register(s1);

        Student s2 = new Student("ali2", "Ali Space 2", "ali@mail.com", "9999"); // same email
        s2.setBirthDate("2000-01-01");
        s2.setMotivationLetterPath("motivation2.pdf");

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> studentService.register(s2)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("email"));
    }



}
