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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void setup() {
        // Очистка данных перед каждым тестом
        facultyRepository.deleteAll();

        // Создание нового факультета
        Faculty faculty = new Faculty("Gryffindor", "Red");
        facultyRepository.save(faculty); // сохраняем тестовые данные
    }

    @Test
    public void testGetAllFaculties() {
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "/faculties",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetFacultyById() {
        Faculty faculty = facultyRepository.findAll().get(0); // Получаем созданный факультет
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + faculty.getId(), Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateFaculty() {
        Faculty newFaculty = new Faculty("Slytherin", "Green");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculties", newFaculty, Faculty.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = facultyRepository.findAll().get(0); // Получаем созданный факультет
        Faculty updatedFaculty = new Faculty("Gryffindor", "Gold");

        // Обновляем факультет
        restTemplate.put("/faculties/" + faculty.getId(), updatedFaculty);

        // Проверяем, что обновление прошло успешно
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + faculty.getId(), Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Gold", response.getBody().getColor());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = facultyRepository.findAll().get(0); // Получаем созданный факультет
        restTemplate.delete("/faculties/" + faculty.getId());
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + faculty.getId(), Faculty.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
