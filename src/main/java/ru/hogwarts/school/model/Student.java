package ru.hogwarts.school.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Student() {}

    public static class Builder {
        private final long id;
        private final String name;
        private final int age;
        private Faculty faculty;

        public Builder(long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Builder withFaculty(Faculty faculty) {
            this.faculty = faculty;
            return this;
        }

        public Student build() {
            Student student = new Student();
            student.id = this.id;
            student.name = this.name;
            student.age = this.age;
            student.faculty = this.faculty;
            return student;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(name, student.name) && Objects.equals(faculty, student.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, faculty);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}

