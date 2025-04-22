package bitlabInternship.lmsSystem.controller;

import bitlabInternship.lmsSystem.entity.Access;
import bitlabInternship.lmsSystem.service.AccessService;
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
class AccessControllerMockMvcTest {

    private MockMvc mockMvc;

    @Mock
    private AccessService accessService;

    @InjectMocks
    private AccessController accessController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accessController).build();
    }

    @Test
    void createAccess_returnsCreatedAccess() throws Exception {
        Access access = new Access();
        access.setId(1L);
        access.setLevel("ADMIN");

        when(accessService.create(any(Access.class))).thenReturn(access);

        mockMvc.perform(post("/access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(access)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.level", is("ADMIN")));
    }

    @Test
    void getAccessById_returnsAccess() throws Exception {
        Access access = new Access();
        access.setId(2L);
        access.setLevel("USER");

        when(accessService.getAccessById(2L)).thenReturn(access);

        mockMvc.perform(get("/access/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level", is("USER")));
    }

    @Test
    void getAccessById_notFound_throwsException() throws Exception {
        when(accessService.getAccessById(99L)).thenReturn(null);

        mockMvc.perform(get("/access/99"))
                .andExpect(status().isInternalServerError())
                .andExpect(result ->
                        result.getResolvedException().getMessage().equals("Access not found"));
    }

    @Test
    void getAllAccesses_returnsAccessList() throws Exception {
        Access a1 = new Access();
        a1.setId(1L);
        a1.setLevel("ADMIN");

        Access a2 = new Access();
        a2.setId(2L);
        a2.setLevel("MODERATOR");

        List<Access> accesses = Arrays.asList(a1, a2);

        when(accessService.getAll()).thenReturn(accesses);

        mockMvc.perform(get("/access"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].level", is("ADMIN")))
                .andExpect(jsonPath("$[1].level", is("MODERATOR")));
    }
}
