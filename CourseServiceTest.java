package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.exception.BadRequestException;
import bitlabInternship.lmsSystem.exception.ResourceNotFoundException;
import bitlabInternship.lmsSystem.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1L);
        course.setName("Java Basics");
    }

    @Test
    void testCreateCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.create(course);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Java Basics", result.getName());
        verify(courseRepository).save(course);
    }

    @Test
    void testGetAllCourses() {
        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Spring Boot");

        List<Course> mockCourses = Arrays.asList(course, course2);
        when(courseRepository.findAll()).thenReturn(mockCourses);

        List<Course> result = courseService.getAll();

        assertEquals(2, result.size());
        assertEquals("Java Basics", result.get(0).getName());
        assertEquals("Spring Boot", result.get(1).getName());
        verify(courseRepository).findAll();
    }

    @Test
    void testCreateCourse_WithEmptyName() {
        Course invalidCourse = new Course();
        invalidCourse.setName("");  // Пустое имя курса

        // Проверяем, что выбрасывается исключение BadRequestException
        assertThrows(BadRequestException.class, () -> courseService.create(invalidCourse));
    }

    @Test
    void testGetCourseByName_NotFound() {
        String courseName = "Non-existing Course";

        // Настроим мок для поиска курса, который не существует
        when(courseRepository.findByName(courseName)).thenReturn(Optional.empty());

        // Проверяем, что выбрасывается ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> courseService.getByName(courseName));
    }

    @Test
    void testGetCourseByName_Found() {
        String courseName = "Java Basics";

        // Настроим мок для поиска существующего курса
        when(courseRepository.findByName(courseName)).thenReturn(Optional.of(course));

        Course foundCourse = courseService.getByName(courseName);

        assertNotNull(foundCourse);
        assertEquals("Java Basics", foundCourse.getName());
        verify(courseRepository).findByName(courseName);
    }
}
