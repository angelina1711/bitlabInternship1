package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Access;
import bitlabInternship.lmsSystem.repository.AccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessService {

    private static final Logger logger = LoggerFactory.getLogger(AccessService.class);

    private final AccessRepository accessRepository;

    public AccessService(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    public Access create(Access access) {
        logger.info("Создание нового доступа");
        logger.debug("Данные доступа: {}", access);  // Логирование данных объекта
        try {
            return accessRepository.save(access);
        } catch (Exception e) {
            logger.error("Ошибка при создании доступа: {}", e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;  // Пробрасываем исключение дальше
        }
    }

    public List<Access> getAll() {
        logger.info("Получение всех уровней доступа");
        try {
            return accessRepository.findAll();
        } catch (Exception e) {
            logger.error("Ошибка при получении всех уровней доступа: {}", e.getMessage(), e); // Логирование с полным стеком ошибки
            throw e;
        }
    }

    public Access getAccessById(Long id) {
        logger.info("Получение доступа по ID: {}", id);
        Optional<Access> access = accessRepository.findById(id);
        if (access.isEmpty()) {
            logger.error("Доступ с ID {} не найден", id);
        }
        return access.orElse(null);
    }
}
