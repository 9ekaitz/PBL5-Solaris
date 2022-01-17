package eus.solaris.solaris.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordConstraintValidatorTest {

    @InjectMocks
    private PasswordConstraintValidator passwordConstraintValidator;

    @Test
    void testPasswordValidation() {
        String password = "Ikastola2.@";
        assertEquals(true, passwordConstraintValidator.isValid(password, null));
    }
    
}
