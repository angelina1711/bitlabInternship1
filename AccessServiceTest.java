package bitlabInternship.lmsSystem.service;

import bitlabInternship.lmsSystem.entity.Access;
import bitlabInternship.lmsSystem.repository.AccessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessServiceTest {

    @InjectMocks
    private AccessService accessService;

    @Mock
    private AccessRepository accessRepository;

    private Access access;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Создаем объект Access для теста
        access = new Access();
        access.setId(1L);
        access.setLevel("Admin");
    }

    @Test
    void testGetAccessById() {
        // Настроим мок для репозитория
        when(accessRepository.findById(1L)).thenReturn(java.util.Optional.of(access));

        // Вызов метода
        Access result = accessService.getAccessById(1L);

        // Проверяем, что результат соответствует ожидаемому
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Admin", result.getLevel());

        // Проверка, что репозиторий был вызван с нужным параметром
        verify(accessRepository).findById(1L);
    }

    @Test
    void testGetAccessByIdNotFound() {
        // Настроим мок для репозитория, чтобы возвращать пустой Optional
        when(accessRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Вызов метода
        Access result = accessService.getAccessById(1L);

        // Проверяем, что результат null, если доступа с таким ID нет
        assertNull(result);

        // Проверка, что репозиторий был вызван с нужным параметром
        verify(accessRepository).findById(1L);
    }
}
