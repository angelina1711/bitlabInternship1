package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Group;
import bitlabInternship.lmsSystem.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceUnitTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void create_validGroup_savesSuccessfully() {
        Group group = new Group();
        group.setName("Java Group");

        when(groupRepository.save(group)).thenReturn(group);

        Group result = groupService.create(group);

        assertNotNull(result);
        assertEquals("Java Group", result.getName());
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void getAll_returnsListOfGroups() {
        Group g1 = new Group();
        g1.setName("Spring Group");
        Group g2 = new Group();
        g2.setName("Hibernate Group");

        when(groupRepository.findAll()).thenReturn(Arrays.asList(g1, g2));

        List<Group> result = groupService.getAll();

        assertEquals(2, result.size());
        assertEquals("Spring Group", result.get(0).getName());
        assertEquals("Hibernate Group", result.get(1).getName());
        verify(groupRepository, times(1)).findAll();
    }
}

