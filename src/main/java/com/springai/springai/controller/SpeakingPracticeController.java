package com.springai.springai.controller;

import com.springai.springai.model.SpeakingScenario;
import com.springai.springai.model.SpeakingSession;
import com.springai.springai.model.PronunciationFeedback;
import com.springai.springai.service.SpeakingPracticeService;
import com.springai.springai.service.PronunciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/speaking")
@CrossOrigin(origins = "*")
public class SpeakingPracticeController {
    @Autowired
    private SpeakingPracticeService speakingPracticeService;

    @Autowired
    private PronunciationService pronunciationService;

    @GetMapping("/scenarios")
    public ResponseEntity<?> getSpeakingScenarios(@RequestParam String difficulty) {
        List<SpeakingScenario> scenarios = speakingPracticeService.getSpeakingScenarios(difficulty);
        return ResponseEntity.ok(scenarios);
    }

    @PostMapping("/session/start")
    public ResponseEntity<?> startSession(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("userId")).longValue();
        Long scenarioId = ((Number) request.get("scenarioId")).longValue();
        
        SpeakingSession session = speakingPracticeService.startSession(userId, scenarioId);
        return ResponseEntity.ok(Map.of("sessionId", session.getId()));
    }

    @PostMapping("/response")
    public ResponseEntity<?> submitResponse(
            @RequestParam(required = false) MultipartFile audio,
            @RequestParam Long sessionId,
            @RequestParam String spokenText) throws Exception {
        
        PronunciationFeedback feedback = pronunciationService.analyzePronunciation(audio, spokenText);
        return ResponseEntity.ok(feedback);
    }

    @PostMapping("/session/end")
    public ResponseEntity<?> endSession(@RequestBody Map<String, Object> request) {
        Long sessionId = ((Number) request.get("sessionId")).longValue();
        speakingPracticeService.endSession(sessionId);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
