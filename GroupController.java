package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Group;
import bitlabInternship.lmsSystem.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public Group create(@RequestBody Group group) {
        logger.info("Запрос на создание новой группы");
        Group newGroup = groupService.create(group);
        logger.info("Группа с ID {} создана", newGroup.getId());
        return newGroup;
    }

    @GetMapping
    public List<Group> getAll() {
        logger.info("Запрос на получение всех групп");
        return groupService.getAll();
    }
}
