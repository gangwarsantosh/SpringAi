package com.springai.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyStatsDTO {
    private LocalDate date;
    
    @JsonProperty("overallScore")
    private Double overallScore;
    
    @JsonProperty("messageCount")
    private Integer messageCount;
    
    @JsonProperty("voiceCount")
    private Integer voiceCount;
    
    @JsonProperty("grammarCorrectionsCount")
    private Integer grammarCorrectionsCount;
    
    @JsonProperty("pronunciationScore")
    private Double pronunciationScore;
    
    @JsonProperty("newWordsLearned")
    private Integer newWordsLearned;
    
    @JsonProperty("sessionDuration")
    private Long sessionDuration;
    
    @JsonProperty("currentStreak")
    private Integer currentStreak;
}
