package security.jwt;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private String jwtSecret = "dGVzdFNlY3JldEtleQ=="; // Clé secrète par défaut pour les tests
    private int jwtExpirationMs = 3600000; // 1 heure

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils(jwtSecret, jwtExpirationMs);
    }

    @Test
    void testGenerateJwtToken() {
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userPrincipal = new UserDetailsImpl(1L, "test@example.com", "firstName", "lastName", true, "pass");

        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        assertEquals("test@example.com", username);
    }

    @Test
    void testGetUserNameFromJwtToken() {
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        String username = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals("test@example.com", username);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        String invalidToken = "invalid.token.value";

        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }

    @Test
    void testValidateJwtToken_ExpiredToken() {
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - jwtExpirationMs - 1000)) // Token expired 1 second ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_MalformedToken() {
        String malformedToken = "malformed.token";

        assertFalse(jwtUtils.validateJwtToken(malformedToken));
    }

    @Test
    void testValidateJwtToken_UnsupportedToken() {
        String unsupportedToken = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .compact(); // No signing

        assertFalse(jwtUtils.validateJwtToken(unsupportedToken));
    }

    @Test
    void testValidateJwtToken_EmptyClaims() {
        String emptyClaimsToken = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        assertTrue(jwtUtils.validateJwtToken(emptyClaimsToken));
    }
}
