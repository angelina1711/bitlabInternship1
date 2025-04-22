package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.exception.BadRequestException;
import bitlabInternship.lmsSystem.exception.ResourceNotFoundException;
import bitlabInternship.lmsSystem.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceUnitTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void create_validCourse_savesSuccessfully() {
        Course course = new Course();
        course.setName("Java");

        when(courseRepository.save(course)).thenReturn(course);

        Course saved = courseService.create(course);

        assertEquals("Java", saved.getName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void create_emptyName_throwsBadRequestException() {
        Course course = new Course();
        course.setName("   "); // пустое имя

        assertThrows(BadRequestException.class, () -> courseService.create(course));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void getAll_returnsCourseList() {
        Course course1 = new Course();
        course1.setName("Java");

        Course course2 = new Course();
        course2.setName("Spring");

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> courses = courseService.getAll();

        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getByName_validName_returnsCourse() {
        Course course = new Course();
        course.setName("Backend");

        when(courseRepository.findByName("Backend")).thenReturn(Optional.of(course));

        Course result = courseService.getByName("Backend");

        assertEquals("Backend", result.getName());
    }

    @Test
    void getByName_nullName_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> courseService.getByName(null));
    }

    @Test
    void getByName_notFound_throwsResourceNotFoundException() {
        when(courseRepository.findByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getByName("Unknown"));
    }
}
