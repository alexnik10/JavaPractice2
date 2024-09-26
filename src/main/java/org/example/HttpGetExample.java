package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class HttpGetExample {

    public static void execute() {
        System.out.print("\n    Задание 4.\n");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/headers"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject headers = jsonObject.getJSONObject("headers");
            String headersAsString = String.join(", ", headers.keySet());
            System.out.println("Заголовки запроса: " + headersAsString);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
