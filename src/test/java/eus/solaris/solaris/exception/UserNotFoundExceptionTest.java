package eus.solaris.solaris.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    @Test
    void testExceptionEmptyConstructor() {
        UserNotFoundException exception = new UserNotFoundException();
        assertEquals(exception.getMessage(), null);
    }

    @Test
    void testExceptionConstructor() {
        UserNotFoundException exception = new UserNotFoundException("test");
        assertEquals(exception.getMessage(), "Could not find user with username test");
    }

    @Test
    void testAssertThrows() {
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException();
        });
    }
}
