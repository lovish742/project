package it.spaceschool.dao;

public final class DAOFactory {

    public enum Mode { MEMORY, FILE_BINARY, MYSQL }

    // default mode
    public static Mode MODE = Mode.MEMORY;

    private DAOFactory() {}

    public static StudentDAO createStudentDAO() {
        return switch (MODE) {
            case FILE_BINARY -> new StudentDAOFileBinary();
            case MYSQL -> new StudentDAOMySQL();
            default -> new StudentDAOMemory();
        };
    }

    public static CourseDAO createCourseDAO() {
        return new CourseDAOMemory();
    }
}
