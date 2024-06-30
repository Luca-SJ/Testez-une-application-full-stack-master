package security.services;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("test@example.com")
                .firstName("Test")
                .lastName("User")
                .admin(false)
                .password("password")
                .build();
    }

    @Test
    void testGetId() {
        assertEquals(1L, userDetails.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("test@example.com", userDetails.getUsername());
    }

    @Test
    void testGetFirstName() {
        assertEquals("Test", userDetails.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("User", userDetails.getLastName());
    }

    @Test
    void testIsAdmin() {
        assertFalse(userDetails.getAdmin());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals_SameObject() {
        assertTrue(userDetails.equals(userDetails));
    }

    @Test
    void testEquals_DifferentObjectSameValues() {
        UserDetailsImpl anotherUserDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("test@example.com")
                .firstName("Test")
                .lastName("User")
                .admin(false)
                .password("password")
                .build();
        assertTrue(userDetails.equals(anotherUserDetails));
    }

    @Test
    void testEquals_DifferentObjectDifferentValues() {
        UserDetailsImpl anotherUserDetails = UserDetailsImpl.builder()
                .id(2L)
                .username("test2@example.com")
                .firstName("Test2")
                .lastName("User2")
                .admin(true)
                .password("password2")
                .build();
        assertFalse(userDetails.equals(anotherUserDetails));
    }

    @Test
    void testEquals_Null() {
        assertFalse(userDetails.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        assertFalse(userDetails.equals("a string"));
    }

}
