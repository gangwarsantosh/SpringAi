package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "speaking_scenarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingScenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String context;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String topic;

    private Integer estimatedDuration;

    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
