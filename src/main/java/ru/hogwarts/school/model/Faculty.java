package ru.hogwarts.school.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private List<Student> students;


    public Faculty() {
    }


    public Faculty(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    // Конструктор с двумя параметрами
    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

    // Геттеры, сеттеры, equals, hashCode, toString

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
