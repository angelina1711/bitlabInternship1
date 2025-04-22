package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Course;
import bitlabInternship.lmsSystem.exception.BadRequestException;
import bitlabInternship.lmsSystem.exception.ResourceNotFoundException;
import bitlabInternship.lmsSystem.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course create(Course course) {
        logger.info("Создание нового курса");

        // ✅ Валидация входных данных
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            logger.error("Имя курса не может быть пустым");
            throw new BadRequestException("Имя курса не может быть пустым");
        }

        logger.debug("Данные курса: {}", course); // Логирование данных объекта
        try {
            return courseRepository.save(course);
        } catch (Exception e) {
            logger.error("Ошибка при создании курса: {}", e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;
        }
    }

    public List<Course> getAll() {
        logger.info("Получение списка всех курсов");
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            logger.error("Ошибка при получении всех курсов: {}", e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;
        }
    }

    public Course getByName(String name) {
        logger.info("Поиск курса по имени: {}", name);

        // ✅ Валидация входных данных
        if (name == null || name.trim().isEmpty()) {
            logger.error("Имя курса не может быть пустым");
            throw new BadRequestException("Имя курса не может быть пустым");
        }

        try {
            return courseRepository.findByName(name)
                    .orElseThrow(() -> {
                        logger.error("Курс с именем {} не найден", name);
                        return new ResourceNotFoundException("Курс с именем \"" + name + "\" не найден");
                    });
        } catch (Exception e) {
            logger.error("Ошибка при поиске курса по имени {}: {}", name, e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;
        }
    }
}
