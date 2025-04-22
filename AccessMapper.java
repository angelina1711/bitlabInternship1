package bitlabInternship.lmsSystem.mapper;

import bitlabInternship.lmsSystem.dto.AccessDto;
import bitlabInternship.lmsSystem.entity.Access;
import org.springframework.stereotype.Component;

@Component
public class AccessMapper {

    public AccessDto toDto(Access access) {
        AccessDto dto = new AccessDto();
        dto.setId(access.getId());
        dto.setLevel(access.getLevel()); // вот тут важно!
        return dto;
    }

    public Access toEntity(AccessDto dto) {
        Access access = new Access();
        access.setId(dto.getId());
        access.setLevel(dto.getLevel()); // и тут тоже
        return access;
    }
}

