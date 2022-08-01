package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodsTest {

    @Test
    void longStringShouldReturnTrue(){
        String tooLong = "This string should be much longer than the actual ability of the database to accept it.";
        assertEquals(true, Methods.stringTooLong(tooLong));
    }
    @Test
    void shortStringShouldReturnFalse(){
        String shortString = "short String";
        assertEquals(false,Methods.stringTooLong(shortString));
    }
}