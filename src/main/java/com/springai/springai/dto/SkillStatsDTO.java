package com.springai.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillStatsDTO {
    @JsonProperty("skillName")
    private String skillName;
    
    private Double score;
    
    private String level;
    
    @JsonProperty("trend")
    private String trend;
    
    @JsonProperty("improvement")
    private Double improvement;
}
