package eus.solaris.solaris.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    @Test
    void testExceptionEmptyConstructor() {
        UserNotFoundException exception = new UserNotFoundException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testExceptionConstructor() {
        UserNotFoundException exception = new UserNotFoundException("test");
        assertEquals("Could not find user with username test", exception.getMessage());
    }

    @Test
    void testAssertThrows() {
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException();
        });
    }
}
