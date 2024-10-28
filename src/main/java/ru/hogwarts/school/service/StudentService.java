package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(long id, Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findStudentsByAgeRange(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findFacultyOfStudent(long id) {
        Student student = findStudent(id);
        return student != null ? student.getFaculty() : null;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}
