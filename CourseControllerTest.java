package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.exception.ResourceNotFoundException;
import bitlabInternship.lmsSystem.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    public void testCreateCourse_Success() throws Exception {
        Course course = new Course();
        course.setName("Test Course");

        when(courseService.create(course)).thenReturn(course);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Course\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Course"));

        verify(courseService, times(1)).create(course);
    }

    @Test
    public void testGetCourseNotFound() throws Exception {
        String courseName = "Non-existent Course";

        when(courseService.getByName(courseName)).thenThrow(new ResourceNotFoundException("Курс не найден"));

        mockMvc.perform(get("/courses/{name}", courseName))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Курс не найден"));
    }
}

