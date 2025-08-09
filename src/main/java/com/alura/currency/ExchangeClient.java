package com.alura.currency;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ExchangeClient {
    private final String baseUrl;
    private final String apiKey;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public ExchangeClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl != null && !baseUrl.isBlank() ? baseUrl : "https://api.exchangerate.host";
        this.apiKey = apiKey;
    }

    public RateResponse latest(String baseCurrency) throws IOException, InterruptedException {
        String uri = String.format("%s/latest?base=%s", baseUrl, baseCurrency);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
            return gson.fromJson(resp.body(), RateResponse.class);
        } else {
            throw new IOException("HTTP " + resp.statusCode() + ": " + resp.body());
        }
    }

    public double convert(String from, String to, double amount) throws IOException, InterruptedException {
        RateResponse rr = latest(from);
        if (rr == null || rr.getRates()==null || !rr.getRates().containsKey(to)) {
            throw new IOException("Taxa não disponível para par " + from + "->" + to);
        }
        double rate = rr.getRates().get(to);
        return amount * rate;
    }
}
