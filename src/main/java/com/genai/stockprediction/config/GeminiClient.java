package com.genai.stockprediction.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiClient {
    private static final String END_POINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    public static String callGeminiApi(String prompt) throws Exception {
        String apiKey = System.getenv("GEMINI_API_KEY");
        URL url = new URL(END_POINT + "?key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String payload = String.format("""
                { "contents":[
                {
                "parts":[
                {"text":"%s"}]
                }]
                }""", prompt.replace("\"", "\\\"").replace("%", "%%"));
        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            response.append(line);
        br.close();

        JsonNode json = new ObjectMapper().readTree(response.toString());
        JsonNode textNode = json.at("/candidates/0/content/parts/0/text");
        return textNode.isMissingNode() ? "No AI response found." : textNode.asText();
    }
}
