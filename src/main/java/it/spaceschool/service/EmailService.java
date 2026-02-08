package it.spaceschool.service;


import it.spaceschool.util.LoggerUtil;
import it.spaceschool.model.Student;

public class EmailService {

    public void sendCourseRegistrationEmail(Student student, String courseId) {
        String message = String.format("""
-------------------------------
TO: %s
SUBJECT: Course Registration Confirmed

Hello %s,

You are successfully registered for:
Course ID: %s

Online Class Link: https://space-school.example.com/class/%s
Time: 10:00 AM (Rome Time)

-------------------------------
""", student.getEmail(), student.getFullName(), courseId, courseId);

        LoggerUtil.info(message);
    }
}
