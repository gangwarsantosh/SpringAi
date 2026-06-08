package com.springai.springai.controller;

import com.springai.springai.model.SkillStats;
import com.springai.springai.model.DailyStats;
import com.springai.springai.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/stats")
    public ResponseEntity<?> getSkillStats(@RequestParam Long userId) {
        List<SkillStats> stats = analyticsService.getUserStats(userId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyStats(@RequestParam Long userId) {
        DailyStats stats = analyticsService.getDailyStats(userId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/progress")
    public ResponseEntity<?> getProgressStats(@RequestParam Long userId, @RequestParam(defaultValue = "all") String timeRange) {
        List<DailyStats> stats = analyticsService.getProgressStats(userId, timeRange);
        return ResponseEntity.ok(stats);
    }
}
