package backend.academy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class URLCheckerTest {

    @Test
    void isValidURL() {
        String validUrl = "https://www.google.com";
        boolean validURL = URLChecker.isValidURL(validUrl);

        assertTrue(validURL);
    }

    @Test
    void isInvalidURL() {
        String invalidUrl = "rerefdfdf3434eeddww";
        boolean invalidURL = URLChecker.isValidURL(invalidUrl);

        assertFalse(invalidURL);
    }
}
