package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        logger.info("Запрос на создание нового курса");
        logger.debug("Данные курса из запроса: {}", course);
        Course newCourse = courseService.create(course);
        logger.info("Курс с ID {} создан", newCourse.getId());
        return newCourse;
    }

    @GetMapping("/{name}")
    public Course getByName(@PathVariable String name) {
        logger.info("Запрос на получение курса с именем: {}", name);
        Course course = courseService.getByName(name);
        logger.info("Курс с именем {} найден", course.getName());
        return course;
    }

    @GetMapping
    public List<Course> getAll() {
        logger.info("Запрос на получение всех курсов");
        List<Course> courses = courseService.getAll();
        if (courses.isEmpty()) {
            logger.warn("Список курсов пуст");
        } else {
            logger.debug("Количество курсов: {}", courses.size());
        }
        return courses;
    }
}
