package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Group;
import bitlabInternship.lmsSystem.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GroupControllerMockMvcTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    void createGroup_returnsCreatedGroup() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setName("Group A");

        when(groupService.create(any(Group.class))).thenReturn(group);

        mockMvc.perform(post("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Group A")));
    }

    @Test
    void getAllGroups_returnsGroupList() throws Exception {
        Group g1 = new Group();
        g1.setId(1L);
        g1.setName("Group A");

        Group g2 = new Group();
        g2.setId(2L);
        g2.setName("Group B");

        List<Group> groups = Arrays.asList(g1, g2);

        when(groupService.getAll()).thenReturn(groups);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Group A")))
                .andExpect(jsonPath("$[1].name", is("Group B")));
    }
}
