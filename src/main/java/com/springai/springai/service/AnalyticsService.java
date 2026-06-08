package com.springai.springai.service;

import com.springai.springai.model.*;
import com.springai.springai.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsService {
    @Autowired
    private SkillStatsRepository skillStatsRepository;

    @Autowired
    private DailyStatsRepository dailyStatsRepository;

    public List<SkillStats> getUserStats(Long userId) {
        return skillStatsRepository.findByUserId(userId);
    }

    public DailyStats getDailyStats(Long userId) {
        Optional<DailyStats> stats = dailyStatsRepository.findByUserIdAndDate(userId, LocalDate.now());
        return stats.orElse(null);
    }

    public List<DailyStats> getProgressStats(Long userId, String timeRange) {
        return dailyStatsRepository.findByUserIdOrderByDateDesc(userId);
    }

    public void updateDailyStats(Long userId) {
        Optional<DailyStats> existingStats = dailyStatsRepository.findByUserIdAndDate(userId, LocalDate.now());
        
        DailyStats stats = existingStats.orElseGet(DailyStats::new);
        User user = new User();
        user.setId(userId);
        stats.setUser(user);
        stats.setDate(LocalDate.now());
        
        dailyStatsRepository.save(stats);
    }

    public void updateSkillStats(Long userId, SkillStats.Skill skill, Double score) {
        User user = new User();
        user.setId(userId);
        
        SkillStats stats = new SkillStats();
        stats.setUser(user);
        stats.setSkillName(skill);
        stats.setScore(score);
        
        skillStatsRepository.save(stats);
    }
}
