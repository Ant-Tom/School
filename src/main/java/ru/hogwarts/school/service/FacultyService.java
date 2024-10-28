package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        if (facultyRepository.existsById(id)) {
            faculty.setId(id);
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findFacultyByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> findStudentsOfFaculty(long id) {
        Faculty faculty = findFaculty(id);
        return faculty != null ? faculty.getStudents() : null;
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

}
