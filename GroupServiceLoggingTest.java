package bitlabInternship.lmsSystem;

import bitlabInternship.lmsSystem.entity.Group;
import bitlabInternship.lmsSystem.repository.GroupRepository;
import bitlabInternship.lmsSystem.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.spi.ILoggingEvent;  // Используем ILoggingEvent

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceLoggingTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    private Group group;
    private ListAppender<ILoggingEvent> listAppender; // Используем ILoggingEvent
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        group = new Group();
        group.setId(1L);
        group.setName("Java Group");

        // Настройка Appender для логирования
        logger = (Logger) LoggerFactory.getLogger(GroupService.class);
        listAppender = new ListAppender<>(); // Создаем ListAppender для ILoggingEvent
        listAppender.start();
        logger.addAppender(listAppender); // Добавляем Appender в Logger
    }

    @Test
    void testCreateGroupLogging() {
        when(groupRepository.save(group)).thenReturn(group);

        // Вызов метода сервиса
        groupService.create(group);

        // Получаем все записанные логи
        List<ILoggingEvent> logsList = listAppender.list;

        // Проверяем, что логи содержат ожидаемые сообщения
        assertTrue(logsList.stream().anyMatch(event -> event.getMessage().equals("Создание новой группы")));
        assertTrue(logsList.stream().anyMatch(event -> event.getMessage().contains("Данные группы:")));
    }

    @Test
    void testCreateGroupLogging_Error() {
        when(groupRepository.save(group)).thenThrow(new RuntimeException("Database error"));

        try {
            groupService.create(group);
        } catch (RuntimeException e) {
            // Получаем все записанные логи
            List<ILoggingEvent> logsList = listAppender.list;

            // Проверяем, что логи содержат ошибку
            assertTrue(logsList.stream().anyMatch(event -> event.getMessage().contains("Ошибка при создании группы:")));
        }
    }
}
