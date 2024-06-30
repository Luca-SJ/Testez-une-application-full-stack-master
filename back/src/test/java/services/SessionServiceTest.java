package services;
import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session session;

    @BeforeEach
    public void setUp() {
        session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());
    }

    @Test
    void testCreateSession() {
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session savedSession = sessionService.create(session);

        assertNotNull(savedSession);
        assertEquals(session.getId(), savedSession.getId());
    }

    @Test
    void testDeleteSession() {
        doNothing().when(sessionRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> sessionService.delete(1L));
    }

    @Test
    void testFindAllSessions() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);

        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> foundSessions = sessionService.findAll();

        assertEquals(1, foundSessions.size());
        assertEquals(session.getId(), foundSessions.get(0).getId());
    }

    @Test
    void testGetByIdSession() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));

        Session foundSession = sessionService.getById(1L);

        assertNotNull(foundSession);
        assertEquals(session.getId(), foundSession.getId());
    }

    @Test
    void testUpdateSession() {
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session updatedSession = sessionService.update(1L, session);

        assertNotNull(updatedSession);
        assertEquals(session.getId(), updatedSession.getId());
    }

    @Test
    void testParticipateInSession_Success() {
        User user = new User();
        user.setId(1L);

        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> sessionService.participate(1L, 1L));
    }

    @Test
    void testParticipateInSession_SessionNotFound() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    @Test
    void testParticipateInSession_UserNotFound() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    @Test
    void testParticipateInSession_AlreadyParticipating() {
        User user = new User();
        user.setId(1L);
        session.getUsers().add(user);

        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L));
    }

    @Test
    void testNoLongerParticipateInSession_Success() {
        User user = new User();
        user.setId(1L);
        session.getUsers().add(user);

        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));

        assertDoesNotThrow(() -> sessionService.noLongerParticipate(1L, 1L));
    }

    @Test
    void testNoLongerParticipateInSession_SessionNotFound() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }

    @Test
    void testNoLongerParticipateInSession_UserNotParticipating() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));

        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }
}
