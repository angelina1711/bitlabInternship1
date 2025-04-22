package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Group;
import bitlabInternship.lmsSystem.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        group = new Group();
        group.setId(1L);
        group.setName("Java Team");
    }

    @Test
    void testCreateGroup() {
        when(groupRepository.save(group)).thenReturn(group);

        Group result = groupService.create(group);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Java Team", result.getName());
        verify(groupRepository).save(group);
    }

    @Test
    void testGetAllGroups() {
        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("Frontend Squad");

        List<Group> mockGroups = Arrays.asList(group, group2);
        when(groupRepository.findAll()).thenReturn(mockGroups);

        List<Group> result = groupService.getAll();

        assertEquals(2, result.size());
        assertEquals("Java Team", result.get(0).getName());
        assertEquals("Frontend Squad", result.get(1).getName());
        verify(groupRepository).findAll();
    }
}

