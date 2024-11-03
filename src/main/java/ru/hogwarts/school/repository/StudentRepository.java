package ru.hogwarts.school.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Поиск студентов по возрастному диапазону
    List<Student> findByAgeBetween(int min, int max);

    // Подсчет количества всех студентов
    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();

    // Получение среднего возраста студентов
    @Query("SELECT AVG(s.age) FROM Student s")
    double getAverageStudentAge();

    // Получение последних пяти студентов
    @Query("SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findLastFiveStudents(Pageable pageable);
}
