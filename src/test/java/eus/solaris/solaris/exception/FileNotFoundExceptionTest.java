package eus.solaris.solaris.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FileNotFoundExceptionTest {

    @Test
    void testExceptionEmptyConstructor() {
        FileNotFoundException exception = new FileNotFoundException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testExceptionConstructor() {
        FileNotFoundException exception = new FileNotFoundException("test");
        assertEquals("Could not find file test", exception.getMessage());
    }

    @Test
    void testAssertThrows() {
        assertThrows(FileNotFoundException.class, () -> {
            throw new FileNotFoundException();
        });
    }
}
