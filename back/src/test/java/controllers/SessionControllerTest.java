package controllers;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        session = new Session();
        session.setId(1L);

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
    }

    @Test
    void testFindById_Success() {
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        when(sessionService.getById(1L)).thenReturn(null);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindById_BadRequest() {
        ResponseEntity<?> response = sessionController.findById("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testFindAll_Success() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);
        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos.add(sessionDto);

        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDtos, response.getBody());
    }

    @Test
    void testCreate_Success() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void testUpdate_Success() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(1L, session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void testUpdate_BadRequest() {
        ResponseEntity<?> response = sessionController.update("invalid", sessionDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDelete_Success() {
        when(sessionService.getById(1L)).thenReturn(session);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).delete(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(sessionService.getById(1L)).thenReturn(null);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionService, never()).delete(anyLong());
    }

    @Test
    void testDelete_BadRequest() {
        ResponseEntity<?> response = sessionController.save("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testParticipate_Success() {
        doNothing().when(sessionService).participate(1L, 1L);

        ResponseEntity<?> response = sessionController.participate("1", "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testParticipate_BadRequest() {
        ResponseEntity<?> response = sessionController.participate("invalid", "1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testNoLongerParticipate_Success() {
        doNothing().when(sessionService).noLongerParticipate(1L, 1L);

        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testNoLongerParticipate_BadRequest() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("invalid", "1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
