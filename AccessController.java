package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Access;
import bitlabInternship.lmsSystem.service.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController {

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping
    public Access create(@RequestBody Access access) {
        logger.info("Запрос на создание нового доступа");
        Access newAccess = accessService.create(access);
        logger.info("Доступ с ID {} создан", newAccess.getId());
        return newAccess;
    }

    @GetMapping("/{id}")
    public Access getAccessById(@PathVariable Long id) {
        logger.info("Запрос на получение доступа с ID: {}", id);
        Access access = accessService.getAccessById(id);
        if (access == null) {
            logger.error("Доступ с ID {} не найден", id);
            throw new RuntimeException("Access not found");
        }
        logger.info("Доступ с ID {} найден", id);
        return access;
    }

    @GetMapping
    public List<Access> getAll() {
        logger.info("Запрос на получение всех доступов");
        return accessService.getAll();
    }
}
