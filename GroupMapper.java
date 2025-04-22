package bitlabInternship.lmsSystem.mapper;

import bitlabInternship.lmsSystem.dto.GroupDto;
import bitlabInternship.lmsSystem.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public GroupDto toDTO(Group group) {
        if (group == null) return null;

        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        return dto;
    }

    public Group toEntity(GroupDto dto) {
        if (dto == null) return null;

        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        return group;
    }
}

