package com.springai.springai.service;

import com.springai.springai.model.PronunciationFeedback;
import com.springai.springai.model.MispronunciationItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PronunciationService {
    @Value("${ollama.api.url:http://localhost:11434}")
    private String ollamaUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PronunciationFeedback analyzePronunciation(MultipartFile audioFile, String spokenText) throws Exception {
        String prompt = "Analyze pronunciation quality of: " + spokenText + "\n" +
                       "Respond with JSON: {\"overallScore\":0-100, \"suggestions\":[...], \"mispronounced\":[{\"word\":\"...\", \"incorrect\":\"...\", \"correct\":\"...\"}]}";

        String response = callOllama(prompt);
        JsonNode jsonNode = objectMapper.readTree(response);

        PronunciationFeedback feedback = new PronunciationFeedback();
        feedback.setOverallScore(jsonNode.get("overallScore").asDouble());
        feedback.setSpokenText(spokenText);

        // Parse suggestions
        List<String> suggestions = new ArrayList<>();
        jsonNode.get("suggestions").forEach(s -> suggestions.add(s.asText()));
        feedback.setSuggestions(suggestions);

        // Parse mispronounced words
        List<MispronunciationItem> mispronounced = new ArrayList<>();
        jsonNode.get("mispronounced").forEach(item -> {
            MispronunciationItem mispronunciationItem = new MispronunciationItem();
            mispronunciationItem.setWord(item.get("word").asText());
            mispronunciationItem.setIncorrect(item.get("incorrect").asText());
            mispronunciationItem.setCorrect(item.get("correct").asText());
            mispronounced.add(mispronunciationItem);
        });
        feedback.setMispronounced(mispronounced);

        return feedback;
    }

    private String callOllama(String prompt) throws Exception {
        String url = ollamaUrl + "/api/generate";
        
        String jsonBody = objectMapper.writeValueAsString(Map.of(
            "model", "llama3.2:1b",
            "prompt", prompt,
            "stream", false
        ));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody));

            return httpClient.execute(httpPost, response -> {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                JsonNode json = objectMapper.readTree(responseBody);
                JsonNode responseNode = json.get("response");
                if (responseNode != null) {
                    return responseNode.asText();
                }
                return "No response from Ollama";
            });
        }
    }
}
