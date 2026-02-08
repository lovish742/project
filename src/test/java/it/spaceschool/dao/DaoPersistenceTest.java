// Author: [REPLACE_WITH_STUDENT_NAME]
// NOTE: Please change the above line to your full name before submission.

package it.spaceschool.dao;

import it.spaceschool.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoPersistenceTest {

    private static final String BIN_FILE = "students.bin";

    @AfterEach
    void cleanup() {
        File f = new File(BIN_FILE);
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    void save_and_find_by_email_binary() {
        StudentDAO dao = new StudentDAOFileBinary();
        Student s = new Student("testuser", "Test User", "test@mail.com", "pass123");
        s.setBirthDate("2000-01-01");
        dao.save(s);

        Optional<Student> found = dao.findByEmail("test@mail.com");
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getMissionCall());
    }
}
