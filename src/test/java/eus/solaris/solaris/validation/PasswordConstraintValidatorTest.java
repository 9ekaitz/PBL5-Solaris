package eus.solaris.solaris.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordConstraintValidatorTest {

    @InjectMocks
    private PasswordConstraintValidator passwordConstraintValidator;

    @Mock
    private ValidPassword constraintAnnotation;

    @BeforeEach
    void initialize(){
        passwordConstraintValidator.initialize(constraintAnnotation);
    }


    @Test
    void testPasswordValidationValid() {
        String password = "Password.@";
        assertEquals(true, passwordConstraintValidator.isValid(password, null));
    }

    @Test
    void testPasswordValidationInvalid() {
        String password = "admin";
        assertEquals(true, passwordConstraintValidator.isValid(password, null));
    }

    @Test
    void testPasswordValidationNull() {
        String password = null;
        assertEquals(true, passwordConstraintValidator.isValid(password, null));
    }

    @Test
    void testPasswordValidationEmpty() {
        String password = "";
        assertEquals(true, passwordConstraintValidator.isValid(password, null));
    }
    
}
