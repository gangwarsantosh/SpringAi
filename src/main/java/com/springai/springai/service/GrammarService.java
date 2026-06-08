package com.springai.springai.service;

import com.springai.springai.model.GrammarCorrection;
import com.springai.springai.repository.GrammarCorrectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.util.Map;

@Service
public class GrammarService {
    @Autowired
    private GrammarCorrectionRepository grammarCorrectionRepository;

    @Value("${ollama.api.url:http://localhost:11434}")
    private String ollamaUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GrammarCorrection checkGrammar(String text) throws Exception {
        String prompt = "Analyze this text for grammar errors and respond with JSON: {\"original\":\"...\", \"corrected\":\"...\", \"explanation\":\"...\", \"rule\":\"...\", \"errorType\":\"...\"}\n\nText: " + text;

        String response = callOllama(prompt);
        
        // Parse JSON response
        JsonNode jsonNode = objectMapper.readTree(response);
        
        GrammarCorrection correction = new GrammarCorrection();
        correction.setOriginal(jsonNode.get("original").asText());
        correction.setCorrected(jsonNode.get("corrected").asText());
        correction.setExplanation(jsonNode.get("explanation").asText());
        correction.setRule(jsonNode.get("rule").asText());
        correction.setErrorType(jsonNode.get("errorType").asText());
        
        return grammarCorrectionRepository.save(correction);
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
