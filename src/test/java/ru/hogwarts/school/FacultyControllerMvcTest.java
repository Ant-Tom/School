package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void testGetAllFaculties() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        faculty.setId(1L); // Set the ID

        when(facultyService.findFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty newFaculty = new Faculty("Ravenclaw", "Blue");
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(newFaculty); // Mocking

        mockMvc.perform(MockMvcRequestBuilders.post("/faculties")
                        .content(asJsonString(newFaculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty updatedFaculty = new Faculty("Ravenclaw", "Silver");
        updatedFaculty.setId(1L); // Set the ID

        // Mock the service method to return the updated faculty
        when(facultyService.editFaculty(eq(1L), any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculties/1")
                        .content(asJsonString(updatedFaculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        doNothing().when(facultyService).deleteFaculty(1L); // Mocking

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculties/1"))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
