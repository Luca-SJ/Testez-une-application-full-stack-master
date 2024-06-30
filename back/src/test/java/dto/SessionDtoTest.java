package dto;
import com.openclassrooms.starterjwt.dto.SessionDto;
import lombok.var;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SessionDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void validateSessionDto_WithValidData_ShouldPass() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Math Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("This is a math session");

        // Act
        var violations = validator.validate(sessionDto);

        // Assert
        assertTrue(violations.isEmpty(), "Validation should pass for valid data");
    }

    @Test
    void validateSessionDto_WithInvalidName_ShouldFail() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName(""); // Blank name

        // Act
        var violations = validator.validate(sessionDto);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail for blank name");
    }

    @Test
    void validateSessionDto_WithNullDate_ShouldFail() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDate(null); // Null date

        // Act
        var violations = validator.validate(sessionDto);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail for null date");
    }

    // Add more test cases to cover other scenarios
}
