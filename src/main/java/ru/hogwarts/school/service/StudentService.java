package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method to add a new student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method to find student with id {}", id);
        return studentRepository.findById(id).orElseThrow(() -> {
            logger.error("There is no student with id = {}", id);
            return new RuntimeException("Student not found");
        });
    }

    public List<String> getStudentNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("А"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAgeOfStudents() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public int getOptimizedSum() {
        int n = 1_000_000;
        return n * (n + 1) / 2;
    }

    public Student editStudent(Long id, Student student) {
        logger.info("Was invoked method to edit student with id {}", id);
        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        }
        logger.warn("Attempt to edit a non-existing student with id {}", id);
        return null;
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method to delete student with id {}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> findAllStudents() {
        logger.info("Was invoked method to get all students");
        return studentRepository.findAll();
    }

    public List<Student> findStudentsByAgeRange(int min, int max) {
        logger.info("Was invoked method to find students by age range {} - {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public long getTotalStudents() {
        logger.info("Was invoked method to get total number of students");
        return studentRepository.countAllStudents();
    }

    public double getAverageAge() {
        logger.info("Was invoked method to get average age of students");
        return studentRepository.getAverageStudentAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method to get last five students");
        return studentRepository.findLastFiveStudents(PageRequest.of(0, 5));
    }

    public Faculty findFacultyOfStudent(Long studentId) {
        logger.info("Was invoked method to find faculty of student with id {}", studentId);
        Student student = findStudent(studentId);
        return student != null ? student.getFaculty() : null;
    }
}
