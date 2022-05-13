package gr.upatras.pocTest;

import java.io.IOException;
import java.net.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpPostRequest {

    private static final String url = "http://localhost:8080/temp";

    public static void main(String[] args) throws URISyntaxException {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"temperature\" : 19.2}"))
                .build();


        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("Page response status code: " + response.statusCode());
                    System.out.println("Page response headers: " + response.headers());
                    String responseBody = response.body();
                    System.out.println(responseBody);

                    return response;
                }).join();;
    }
}
