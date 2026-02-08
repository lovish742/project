CREATE DATABASE IF NOT EXISTS space_school;
USE space_school;

CREATE TABLE IF NOT EXISTS students (
    username VARCHAR(100) PRIMARY KEY,
    full_name VARCHAR(200) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    birth_date VARCHAR(20),
    motivation_letter_path VARCHAR(500),
    enrolled_course_ids TEXT
);

CREATE TABLE IF NOT EXISTS courses (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    level VARCHAR(50) NOT NULL,
    duration_hours INT NOT NULL
);

CREATE TABLE IF NOT EXISTS registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES students(username),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
