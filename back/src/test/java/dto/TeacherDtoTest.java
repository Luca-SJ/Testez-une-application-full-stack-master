package dto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import lombok.var;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

public class TeacherDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void validateTeacherDto_WithValidData_ShouldPass() {
        // Arrange
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setLastName("Doe");
        teacherDto.setFirstName("John");

        // Act
        var violations = validator.validate(teacherDto);

        // Assert
        assertTrue(violations.isEmpty(), "Validation should pass for valid data");
    }

    @Test
    void validateTeacherDto_WithBlankLastName_ShouldFail() {
        // Arrange
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setLastName(""); // Blank last name

        // Act
        var violations = validator.validate(teacherDto);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail for blank last name");
    }

    @Test
    void validateTeacherDto_WithNullFirstName_ShouldFail() {
        // Arrange
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName(null); // Null first name

        // Act
        var violations = validator.validate(teacherDto);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail for null first name");
    }

    // Add more test cases to cover other scenarios
}
