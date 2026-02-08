package it.spaceschool.model;

import java.util.Objects;

public class Course {

    private final String id;        // e.g. "ASTRO101"
    private final String name;      // e.g. "Astronomy 101"
    private final String level;     // e.g. "base", "adv"
    private final int durationHours; // e.g. 10

    public Course(String id, String name, String level, int durationHours) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Course id cannot be empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (level == null || level.isBlank()) {
            throw new IllegalArgumentException("Course level cannot be empty");
        }
        if (durationHours <= 0) {
            throw new IllegalArgumentException("Course duration must be > 0");
        }

        this.id = id;
        this.name = name;
        this.level = level;
        this.durationHours = durationHours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public int getDurationHours() {
        return durationHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
