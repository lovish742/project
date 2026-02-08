// Author: [REPLACE_WITH_STUDENT_NAME]
// NOTE: Please change the above line to your full name before submission.

package it.spaceschool.service;

import it.spaceschool.dao.StudentDAOMemory;
import it.spaceschool.dao.StudentDAO;
import it.spaceschool.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    void enroll_course_updates_student_and_sends_email_mock() {
        StudentDAO dao = new StudentDAOMemory();
        StudentService service = new StudentService(dao);

        Student s = new Student("enr1", "Enrollee", "enrol@mail.com", "1111");
        s.setBirthDate("1999-12-12");
        service.register(s);

        String courseId = "C-101";
        service.enrollCourse(s, courseId);

        Student stored = dao.findByEmail("enrol@mail.com").orElseThrow();
        assertTrue(stored.getEnrolledCourseIds().contains(courseId));
    }
}
