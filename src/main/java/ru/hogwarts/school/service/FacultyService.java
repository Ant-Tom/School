package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method to add a new faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method to find faculty with id {}", id);
        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("There is no faculty with id = {}", id);
            return new RuntimeException("Faculty not found");
        });
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        logger.info("Was invoked method to edit faculty with id {}", id);
        if (facultyRepository.existsById(id)) {
            faculty.setId(id);
            return facultyRepository.save(faculty);
        }
        logger.warn("Attempt to edit a non-existing faculty with id {}", id);
        return null;
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method to delete faculty with id {}", id);
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findFacultyByNameOrColor(String name, String color) {
        logger.info("Was invoked method to find faculty by name '{}' or color '{}'", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> findStudentsOfFaculty(long id) {
        logger.info("Was invoked method to find students of faculty with id {}", id);
        Faculty faculty = findFaculty(id);
        return faculty != null ? faculty.getStudents() : null;
    }

    public List<Faculty> getAllFaculties() {
        logger.info("Was invoked method to get all faculties");
        return facultyRepository.findAll();
    }
}
