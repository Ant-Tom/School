package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Используем аннотацию для инициализации Mockito
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository; // Мокаем репозиторий

    @InjectMocks
    private StudentService studentService; // Реальный экземпляр для тестирования логики

    @Test
    void testPrintStudentsInParallel() {
        // Создаем список студентов для теста
        List<Student> students = List.of(
                new Student.Builder(1L, "Harry", 20).build(),
                new Student.Builder(2L, "Hermione", 20).build(),
                new Student.Builder(3L, "Ron", 20).build(),
                new Student.Builder(4L, "Draco", 20).build(),
                new Student.Builder(5L, "Luna", 20).build(),
                new Student.Builder(6L, "Neville", 20).build()
        );

        // Мокаем метод findAll для возврата списка студентов
        when(studentRepository.findAll()).thenReturn(students);

        // Мокаем сам метод printStudentName внутри StudentService
        StudentService spyStudentService = spy(studentService); // Создаем шпион для реального объекта

        // Выполняем тестируемый метод
        spyStudentService.printStudentsInParallel(); // Это реальный метод, который мы тестируем

        // Создаем ArgumentCaptor для захвата объектов Student
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);

        // Проверяем, что метод printStudentName был вызван для каждого студента
        verify(spyStudentService, times(students.size())).printStudentName(captor.capture());

        // Получаем все захваченные аргументы
        List<Student> capturedStudents = captor.getAllValues();

        // Убедимся, что каждый студент был распечатан
        capturedStudents.forEach(student -> System.out.println(student.getName()));

        // Проверка, что метод printStudentName был вызван для каждого студента
        for (Student student : students) {
            assert capturedStudents.stream().anyMatch(s -> s.getName().equals(student.getName()));
        }
    }
}

