package eus.solaris.solaris.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FileNotFoundExceptionTest {

    @Test
    void testExceptionEmptyConstructor() {
        FileNotFoundException exception = new FileNotFoundException();
        assertEquals(exception.getMessage(), null);
    }

    @Test
    void testExceptionConstructor() {
        FileNotFoundException exception = new FileNotFoundException("test");
        assertEquals(exception.getMessage(), "Could not find file test");
    }

    @Test
    void testAssertThrows() {
        assertThrows(FileNotFoundException.class, () -> {
            throw new FileNotFoundException();
        });
    }
}
