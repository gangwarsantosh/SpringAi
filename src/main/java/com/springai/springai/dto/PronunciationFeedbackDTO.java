package com.springai.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PronunciationFeedbackDTO {
    @JsonProperty("overallScore")
    private Double overallScore;
    
    @JsonProperty("spokenText")
    private String spokenText;
    
    @JsonProperty("recordingUrl")
    private String recordingUrl;
    
    @JsonProperty("mispronounced")
    private List<MispronunciationDTO> mispronounced;
    
    private List<String> suggestions;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class MispronunciationDTO {
    private String word;
    private String incorrect;
    private String correct;
}
