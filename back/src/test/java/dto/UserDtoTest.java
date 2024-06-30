package dto;
import com.openclassrooms.starterjwt.dto.UserDto;
import lombok.var;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UserDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void validateUserDto_WithValidData_ShouldPass() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setAdmin(true);
        userDto.setPassword("password123");

        // Act
        var violations = validator.validate(userDto);

        // Assert
        assertTrue(violations.isEmpty(), "Validation should pass for valid data");
    }

    @Test
    void validateUserDto_WithInvalidEmail_ShouldFail() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("invalid_email");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setAdmin(true);
        userDto.setPassword("password123");

        // Act
        var violations = validator.validate(userDto);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail for invalid email");
    }

    // Add more test cases to cover other scenarios
}
