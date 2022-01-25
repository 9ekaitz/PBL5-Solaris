package eus.solaris.solaris.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AvatarNotCreatedExceptionTest {

    @Test
    void testExceptionEmptyConstructor() {
        AvatarNotCreatedException exception = new AvatarNotCreatedException();
        assertEquals(exception.getMessage(), null);
    }

    @Test
    void testExceptionConstructor() {
        AvatarNotCreatedException exception = new AvatarNotCreatedException("test");
        assertEquals(exception.getMessage(), "Could not create avatr picture for user test");
    }

    @Test
    void testAssertThrows() {
        assertThrows(AvatarNotCreatedException.class, () -> {
            throw new AvatarNotCreatedException();
        });
    }
}
