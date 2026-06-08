package com.springai.springai.controller;

import com.springai.springai.model.GrammarCorrection;
import com.springai.springai.service.GrammarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/grammar")
@CrossOrigin(origins = "*")
public class GrammarController {
    @Autowired
    private GrammarService grammarService;

    @GetMapping("/corrections")
    public ResponseEntity<?> getCorrections() {
        // Return recent corrections or sample corrections
        return ResponseEntity.ok(Map.of("message", "Get corrections list"));
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkGrammar(@RequestBody Map<String, String> request) throws Exception {
        String text = request.get("text");
        GrammarCorrection correction = grammarService.checkGrammar(text);
        return ResponseEntity.ok(correction);
    }
}
