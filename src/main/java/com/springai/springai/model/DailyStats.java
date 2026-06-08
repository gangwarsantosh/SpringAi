package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "daily_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate date = LocalDate.now();

    private Double overallScore;

    private Integer messageCount = 0;

    private Integer voiceCount = 0;

    private Integer grammarCorrectionsCount = 0;

    private Double pronunciationScore;

    private Integer newWordsLearned = 0;

    private Long sessionDuration = 0L;

    private Integer currentStreak = 0;
}
