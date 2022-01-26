package eus.solaris.solaris.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AvatarNotCreatedExceptionTest {

    @Test
    void testExceptionEmptyConstructor() {
        AvatarNotCreatedException exception = new AvatarNotCreatedException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testExceptionConstructor() {
        AvatarNotCreatedException exception = new AvatarNotCreatedException("test");
        assertEquals("Could not create avatr picture for user test", exception.getMessage());
    }

    @Test
    void testAssertThrows() {
        assertThrows(AvatarNotCreatedException.class, () -> {
            throw new AvatarNotCreatedException();
        });
    }
}
