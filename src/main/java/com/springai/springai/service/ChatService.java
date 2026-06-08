package com.springai.springai.service;

import com.springai.springai.model.*;
import com.springai.springai.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ClassicHttpResponse;
import java.util.*;

@Service
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${openai.api.key:}")
    private String openAiKey;

    @Value("${ollama.api.url:http://localhost:11434}")
    private String ollamaUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Message sendMessage(Long userId, String content, String conversationId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        
        Conversation conversation;
        if (conversationId != null) {
            // Try to find existing conversation, if not found, create new one with the provided ID
            conversation = conversationRepository.findById(conversationId)
                .orElseGet(() -> createConversationWithId(user, conversationId));
        } else {
            // No conversation ID provided, create a new conversation
            conversation = createConversation(user);
        }

        // Get AI response
        String aiResponse = getAiResponse(content);

        // Save user message
        Message userMessage = new Message();
        userMessage.setUser(user);
        userMessage.setConversation(conversation);
        userMessage.setContent(content);
        userMessage.setSender(Message.MessageSender.USER);
        messageRepository.save(userMessage);

        // Save AI response
        Message aiMessage = new Message();
        aiMessage.setUser(user);
        aiMessage.setConversation(conversation);
        aiMessage.setContent(aiResponse);
        aiMessage.setSender(Message.MessageSender.AI);
        messageRepository.save(aiMessage);

        return aiMessage;
    }

    public List<Message> getChatHistory(String conversationId) {
        return messageRepository.findByConversationIdOrderByTimestampDesc(conversationId);
    }

    private String getAiResponse(String userMessage) throws Exception {
        try {
            // Try OpenAI first
            if (openAiKey != null && !openAiKey.isEmpty()) {
                return callOpenAi(userMessage);
            }
        } catch (Exception e) {
            System.out.println("OpenAI failed, trying Ollama: " + e.getMessage());
        }

        // Fallback to Ollama
        try {
            return callOllama(userMessage);
        } catch (Exception e) {
            System.out.println("Ollama failed: " + e.getMessage());
            return "I'm having trouble processing your request. Please try again.";
        }
    }

    private String callOpenAi(String message) throws Exception {
        String url = "https://api.openai.com/v1/chat/completions";
        
        String jsonBody = objectMapper.writeValueAsString(Map.of(
            "model", "gpt-3.5-turbo",
            "messages", new Object[]{
                Map.of("role", "system", "content", "You are an English teaching assistant."),
                Map.of("role", "user", "content", message)
            },
            "temperature", 0.7
        ));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + openAiKey);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody));

            return httpClient.execute(httpPost, response -> {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                JsonNode json = objectMapper.readTree(responseBody);
                return json.get("choices").get(0).get("message").get("content").asText();
            });
        }
    }

    private String callOllama(String message) throws Exception {
        String url = ollamaUrl + "/api/generate";
        
        String jsonBody = objectMapper.writeValueAsString(Map.of(
            "model", "llama3.2:1b",
            "prompt", message,
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

    private Conversation createConversation(User user) {
        Conversation conversation = new Conversation();
        // Generate unique string ID
        conversation.setId("conv_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8));
        conversation.setUser(user);
        conversation.setTitle("Conversation - " + new Date());
        return conversationRepository.save(conversation);
    }

    private Conversation createConversationWithId(User user, String conversationId) {
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setUser(user);
        conversation.setTitle("Conversation - " + new Date());
        return conversationRepository.save(conversation);
    }
}
