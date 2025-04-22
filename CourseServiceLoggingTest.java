package bitlabInternship.lmsSystem;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.repository.CourseRepository;
import bitlabInternship.lmsSystem.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceLoggingTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    private Course course;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1L);
        course.setName("Java Basics");

        // Настройка Appender
        logger = (Logger) LoggerFactory.getLogger(CourseService.class);
        listAppender = new ListAppender<>(); // Correct type: ILoggingEvent
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void testCreateCourseLogging() {
        when(courseRepository.save(course)).thenReturn(course);

        // Вызов метода сервиса
        courseService.create(course);

        // Получаем все записанные логи
        List<ILoggingEvent> logsList = listAppender.list;

        // Проверяем, что логи содержат ожидаемые сообщения
        assertTrue(logsList.stream().anyMatch(event -> event.getMessage().equals("Создание нового курса")));
        assertTrue(logsList.stream().anyMatch(event -> event.getMessage().contains("Данные курса:")));
    }

    @Test
    void testCreateCourseLogging_Error() {
        when(courseRepository.save(course)).thenThrow(new RuntimeException("Database error"));

        try {
            courseService.create(course);
        } catch (RuntimeException e) {
            // Получаем все записанные логи
            List<ILoggingEvent> logsList = listAppender.list;

            // Проверяем, что логи содержат ошибку
            assertTrue(logsList.stream().anyMatch(event -> event.getMessage().contains("Ошибка при создании курса:")));
        }
    }
}
