# Space School Registration System — Software Requirements Specification (SRS)

## 1. Project Overview
**Project name:** Space School Registration System  
**Author:** Student (solo) — Project refined by Lovish  
**Deadline:** January 19, 2026  
**Language / Runtime:** Java 21, JavaFX, Maven  
**Purpose:** Desktop application that allows visitors to register, students to log in, browse courses, and register for online classes. The system supports dual persistency: Demo (in-memory) and Full (MySQL or Binary File) using DAO + Factory pattern.

## 2. Scope
The system is intended for the "Space School" online academy. Features include:
- Visitor registration (with motivation letter upload and policy checkbox)
- Student login and profile
- Browse and register for courses
- Email notification (simulated) on successful registration
- Dual persistency (Memory, MySQL, Binary File)
- MVC architecture and SonarCloud zero-violation target

## 3. Actors
- **Visitor** — a non-registered user who can register an account.
- **Student** — a registered and authenticated user who can browse and register for courses.
- **System Administrator (implicit)** — configures DAOFactory mode and the DB (outside scope for exam).

## 4. User Stories
- **US-1 (Registration):** As a Visitor, I want to create an account with my mission call and upload a motivation letter so that I may apply to the Space Academy.
- **US-2 (Browse Courses):** As a Student, I want to see all available online courses on my dashboard so I can choose which to register for.
- **US-3 (Registration Confirmation):** As a Student, I want to register to a course and receive an automated email with the link and schedule.

## 5. Functional Requirements (selected)
- **FR-1:** The system shall validate that the mission call is unique and the email is well-formed during registration.
- **FR-2:** The system shall display the student's profile on the left and a list of available courses on the right in the dashboard UI.
- **FR-3:** The system shall support a configuration switch to run in Demo (in-memory) or Full (MySQL OR Binary File) persistency modes.

## 6. Non-Functional Requirements
- **NFR-1 (Code quality):** Project must pass SonarCloud with zero violations (no smells, no bugs, no vulnerabilities).
- **NFR-2 (Testing):** At least 3 JUnit test cases must be present (per project). Tests must include author comments.
- **NFR-3 (Delivery):** Provide SRS PDF, UML diagrams, codebase zip, SQL script, JUnit tests, and a 1–2 minute demo video.

## 7. Architecture & Design
- **Pattern:** MVC (Model-View-Controller) with DAO + Factory for persistence.
- **DAO choices:** `StudentDAOMemory`, `StudentDAOMySQL`, `StudentDAOFileBinary` (serializable).
- **Factory:** `DAOFactory.Mode` {MEMORY, FILE_BINARY, MYSQL} to switch implementations.

## 8. Data Model (summary)
- **Student**
  - missionCall (unique)
  - firstName
  - lastName
  - email (unique)
  - password
  - birthDate
  - motivationLetterPath
  - enrolledCourseIds (list / comma-separated)
- **Course**
  - id
  - name
  - level
  - durationHours

## 9. Persistence
- **Demo:** `StudentDAOMemory` — transient in-memory lists.
- **File System (Full):** `StudentDAOFileBinary` — stores `List<Student>` in `students.bin` via Java serialization.
- **DBMS (Full):** `StudentDAOMySQL` — JDBC implementation with schema `space_school` (see `space_school.sql`).

## 10. API / Behavioral Flows
### Course Registration (sequence summary)
1. Student selects a course and clicks Register.
2. Controller calls `StudentService.enrollCourse(student, courseId)`.
3. Service updates the Student model and calls `studentDAO.update(student)`.
4. Service calls `emailService.sendCourseRegistrationEmail(student, courseId)` (mock).
5. UI shows success message.

## 11. Test Plan
Included tests (see `/src/test/java`):
- `StudentServiceTest.java` — registration and login scenarios (existing).
- `DaoPersistenceTest.java` — binary file DAO save/find behaviour.
- `EnrollmentTest.java` — enrollment flow and persistence.

Each test file includes author comments (student name placeholder).

## 12. Deployment and Setup
1. Import `space_school.sql` to MySQL to create the database.
2. Update `DBConnection.java` with DB credentials.
3. Build project in IntelliJ (Maven).
4. Select persistence mode in `DAOFactory` if necessary.

## 13. Deliverables included in the ZIP
- Updated IntelliJ project (complete source)
- `space_school.sql` script
- `students.bin` (created when binary DAO used)
- SRS (this file) — `SRS.md`
- Demo script — `demo_script.txt`
- README with instructions
- JUnit tests (3 total)
- Diagrams placeholders and draw.io instructions in `diagrams/` (you will generate final images)

---

### Appendix A — Draw.io diagram instructions (copy to draw.io > File > Import)
1. **Use Case**: Actors: Visitor, Student. Use cases: Register, Login, View Profile, Browse Courses, Register for Course, Receive Email.
2. **Sequence**: Student -> CourseController -> StudentService -> StudentDAO -> EmailService (save -> send email -> response).
3. **State**: Registration entity: Created -> Validating -> Registered -> EmailSent -> Completed.
4. **Class**: Show `Student`, `Course`, `StudentDAO` (interface), `StudentDAOMemory`, `StudentDAOMySQL`, `StudentDAOFileBinary`, `DAOFactory`, `StudentService`, `EmailService`. Indicate `DAOFactory` returns implementations.

