package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Access;
import bitlabInternship.lmsSystem.repository.AccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessServiceUnitTest {

    @Mock
    private AccessRepository accessRepository;

    @InjectMocks
    private AccessService accessService;

    @Test
    void create_validAccess_savesSuccessfully() {
        Access access = new Access();
        access.setLevel("ADMIN");

        when(accessRepository.save(access)).thenReturn(access);

        Access result = accessService.create(access);

        assertNotNull(result);
        assertEquals("ADMIN", result.getLevel());
        verify(accessRepository, times(1)).save(access);
    }

    @Test
    void getAll_returnsListOfAccess() {
        Access a1 = new Access();
        a1.setLevel("USER");

        Access a2 = new Access();
        a2.setLevel("MODERATOR");

        when(accessRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Access> result = accessService.getAll();

        assertEquals(2, result.size());
        assertEquals("USER", result.get(0).getLevel());
        assertEquals("MODERATOR", result.get(1).getLevel());
        verify(accessRepository, times(1)).findAll();
    }

    @Test
    void getAccessById_existingId_returnsAccess() {
        Access access = new Access();
        access.setId(1L);
        access.setLevel("ADMIN");

        when(accessRepository.findById(1L)).thenReturn(Optional.of(access));

        Access result = accessService.getAccessById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ADMIN", result.getLevel());
        verify(accessRepository, times(1)).findById(1L);
    }

    @Test
    void getAccessById_nonExistingId_returnsNull() {
        when(accessRepository.findById(99L)).thenReturn(Optional.empty());

        Access result = accessService.getAccessById(99L);

        assertNull(result);
        verify(accessRepository, times(1)).findById(99L);
    }
}
