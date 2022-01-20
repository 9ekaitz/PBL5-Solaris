package eus.solaris.solaris.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordConstraintValidatorTest {

    @InjectMocks
    private PasswordConstraintValidator passwordConstraintValidator;

    @Mock
    private ValidPassword constraintAnnotation;

    @BeforeEach
    void initialize(){
        passwordConstraintValidator.initialize(constraintAnnotation);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password.@", "admin", ""})
    void testPasswordValidationValid(String arg){
        assertEquals(true, passwordConstraintValidator.isValid(arg, null));
    }
    
}
