package bitlabInternship.lmsSystem.mapper;

import bitlabInternship.lmsSystem.dto.CourseDto;
import bitlabInternship.lmsSystem.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDto toDTO(Course course) {
        if (course == null) return null;

        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setName(course.getName());
        return dto;
    }

    public Course toEntity(CourseDto dto) {
        if (dto == null) return null;

        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        return course;
    }
}
