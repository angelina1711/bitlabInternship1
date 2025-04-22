package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerMockMvcTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void createCourse_returnsCreatedCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Java 101");

        when(courseService.create(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Java 101")));
    }

    @Test
    void getCourseByName_returnsCourse() throws Exception {
        Course course = new Course();
        course.setId(2L);
        course.setName("Spring Boot");

        when(courseService.getByName("Spring Boot")).thenReturn(course);

        mockMvc.perform(get("/courses/Spring Boot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Spring Boot")));
    }

    @Test
    void getAllCourses_returnsCourseList() throws Exception {
        Course c1 = new Course();
        c1.setId(1L);
        c1.setName("Java");

        Course c2 = new Course();
        c2.setId(2L);
        c2.setName("Spring");

        List<Course> courses = Arrays.asList(c1, c2);

        when(courseService.getAll()).thenReturn(courses);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Java")))
                .andExpect(jsonPath("$[1].name", is("Spring")));
    }
}
