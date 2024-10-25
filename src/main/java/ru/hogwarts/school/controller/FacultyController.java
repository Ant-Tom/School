package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        return faculty != null ? ResponseEntity.ok(faculty) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty, @PathVariable Long id) {
        Faculty updatedFaculty = facultyService.editFaculty(id, faculty);
        return updatedFaculty != null ? ResponseEntity.ok(updatedFaculty) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> getFacultyByNameOrColor(@RequestParam String query) {
        return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(query, query));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getFacultyStudents(@PathVariable Long id) {
        List<Student> students = facultyService.findStudentsOfFaculty(id);
        return students != null ? ResponseEntity.ok(students) : ResponseEntity.notFound().build();
    }
}
