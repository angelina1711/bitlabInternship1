package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Group;
import bitlabInternship.lmsSystem.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group create(Group group) {
        logger.info("Создание новой группы");
        logger.debug("Данные группы: {}", group);  // Логирование данных группы
        try {
            return groupRepository.save(group);
        } catch (Exception e) {
            logger.error("Ошибка при создании группы: {}", e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;
        }
    }

    public List<Group> getAll() {
        logger.info("Получение всех групп");
        try {
            return groupRepository.findAll();
        } catch (Exception e) {
            logger.error("Ошибка при получении всех групп: {}", e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;
        }
    }
}
