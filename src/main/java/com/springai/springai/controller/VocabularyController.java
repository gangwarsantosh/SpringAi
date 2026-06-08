package com.springai.springai.controller;

import com.springai.springai.model.VocabularyWord;
import com.springai.springai.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vocabulary")
@CrossOrigin(origins = "*")
public class VocabularyController {
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping("/list")
    public ResponseEntity<?> getVocabularyList() {
        List<VocabularyWord> words = vocabularyService.getVocabularyList();
        return ResponseEntity.ok(words);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchVocabulary(@RequestParam String query) {
        List<VocabularyWord> words = vocabularyService.searchVocabulary(query);
        return ResponseEntity.ok(words);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveWord(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("userId")).longValue();
        Long wordId = ((Number) request.get("wordId")).longValue();
        
        vocabularyService.saveWordForUser(userId, wordId);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/remove/{wordId}")
    public ResponseEntity<?> removeWord(@PathVariable Long wordId, @RequestParam Long userId) {
        vocabularyService.removeWordForUser(userId, wordId);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
