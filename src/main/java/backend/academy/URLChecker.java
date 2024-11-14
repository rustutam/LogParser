package backend.academy;

import java.net.URI;

public class URLChecker {
    private URLChecker() {
    }

    public static boolean isValidURL(String urlString) {
        try {
            URI uri = new URI(urlString);
            uri.toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
