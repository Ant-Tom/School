package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Long existingStudentId;

    @BeforeEach
    public void setUp() {
        Student newStudent = new Student.Builder(0L, "Harry Potter", 16).build();
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", newStudent, Student.class);
        existingStudentId = response.getBody().getId(); // Получаем ID созданного студента
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetStudentById() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + existingStudentId, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student.Builder(0L, "Hermione Granger", 16).build();
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", newStudent, Student.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateStudent() {
        Student updatedStudent = new Student.Builder(existingStudentId, "Harry Potter", 17).build();
        restTemplate.put("/students/" + existingStudentId, updatedStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + existingStudentId, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(17, response.getBody().getAge());
    }

    @Test
    public void testDeleteStudent() {
        restTemplate.delete("/students/" + existingStudentId);
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + existingStudentId, Student.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
