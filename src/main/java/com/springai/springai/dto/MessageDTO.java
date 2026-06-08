package com.springai.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    
    @JsonProperty("sender")
    private String sender;
    
    private String content;
    
    private Date timestamp;
    
    @JsonProperty("audioUrl")
    private String audioUrl;
    
    @JsonProperty("grammarCorrection")
    private Object grammarCorrection;
    
    @JsonProperty("pronunciationFeedback")
    private Object pronunciationFeedback;
    
    private String sentiment;
}
