package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pronunciation_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PronunciationFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double overallScore;

    private String spokenText;

    private String recordingUrl;

    @ElementCollection
    private List<String> suggestions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "feedback_id")
    private List<MispronunciationItem> mispronounced = new ArrayList<>();
}
