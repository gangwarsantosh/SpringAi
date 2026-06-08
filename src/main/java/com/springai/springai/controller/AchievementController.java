package com.springai.springai.controller;

import com.springai.springai.model.Achievement;
import com.springai.springai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin(origins = "*")
public class AchievementController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> getAchievements(@RequestParam Long userId) {
        List<Achievement> achievements = userService.getUserAchievements(userId);
        return ResponseEntity.ok(achievements);
    }
}
