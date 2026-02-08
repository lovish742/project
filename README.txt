SpaceSchool — Final Deliverables (Prepared by Lovish)

Contents:
- Source code (Maven, Java 21, JavaFX)
- space_school.sql — MySQL schema
- SRS.md — Software Requirements Specification
- demo_script.txt — script for 1-2 minute demo
- src/test/java/... — 3 JUnit tests included
- diagrams/ — placeholder files and draw.io import instructions

Quick start:
1. Unzip and open the project in IntelliJ as a Maven project.
2. Edit DB credentials in src/main/java/it/spaceschool/dao/DBConnection.java if you will use MySQL.
3. Run `mvn test` to execute unit tests, or use IntelliJ Run.
4. To switch persistence:
   - Open src/main/java/it/spaceschool/dao/DAOFactory.java and set MODE to MEMORY, FILE_BINARY, or MYSQL.
5. For Binary mode, the file `students.bin` will be created in the working directory.
6. For MySQL mode: import `space_school.sql` into your MySQL server, then run.

Notes:
- Email sending is simulated by printing the email contents to the console (acceptable for university submission).
- Diagrams will be provided separately; Draw.io instructions are included in SRS.md.
- SonarCloud cleanup: basic logging changes applied; further fixes may be required depending on SonarCloud rules.
