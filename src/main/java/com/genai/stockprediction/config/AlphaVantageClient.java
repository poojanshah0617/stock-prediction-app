package com.genai.stockprediction.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class AlphaVantageClient {
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private static final String API_KEY = System.getenv("ALPHA_VANTAGE_API_KEY");

    public static Map<String, Double> fetchDailyClosePrices(String symbol) throws IOException {
        URL url = new URL(BASE_URL + "?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        bufferedReader.close();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonBuilder.toString());
        JsonNode timeSeries = root.path("Time Series (Daily)");

        if (timeSeries.isMissingNode()) {
            throw new RuntimeException("Invalid API response or API limit exceeded.");
        }
        Map<String, Double> dailyPrices = new TreeMap<>();

        for (Iterator<String> it = timeSeries.fieldNames(); it.hasNext(); ) {
            String date = it.next();
            double close = timeSeries.get(date).get("4. close").asDouble();
            dailyPrices.put(date, close);
        }

        return dailyPrices;
    }
}
