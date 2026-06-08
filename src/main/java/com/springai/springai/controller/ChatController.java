package com.springai.springai.controller;

import com.springai.springai.model.Message;
import com.springai.springai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, Object> request) throws Exception {
        Long userId = ((Number) request.get("userId")).longValue();
        String message = (String) request.get("message");
        
        // Handle conversationId - can come as String or Number
        String conversationId = null;
        Object convIdObj = request.get("conversationId");
        if (convIdObj != null) {
            if (convIdObj instanceof String) {
                conversationId = (String) convIdObj;
            } else if (convIdObj instanceof Number) {
                conversationId = ((Number) convIdObj).toString();
            }
        }

        Message response = chatService.sendMessage(userId, message, conversationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{conversationId}")
    public ResponseEntity<?> getChatHistory(@PathVariable String conversationId) {
        List<Message> history = chatService.getChatHistory(conversationId);
        return ResponseEntity.ok(history);
    }
}
