package backend.academy.readers;

import lombok.extern.log4j.Log4j2;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Log4j2
public class HTTPReader implements Reader{
    private String url;

    public HTTPReader(String url) {
        this.url = url;
    }

    @Override
    public Stream<String> readLogs() {
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();

            HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
            return StreamSupport.stream(response.body().spliterator(), false);
        } catch (Exception e) {
            log.error("Ошибка в {}", e.getClass());
        }
        return Stream.empty();

    }
}
