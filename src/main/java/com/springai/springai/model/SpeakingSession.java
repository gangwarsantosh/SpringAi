package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "speaking_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    private SpeakingScenario scenario;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    private LocalDateTime startTime = LocalDateTime.now();

    private LocalDateTime endTime;

    private Double score;

    public enum SessionStatus {
        STARTED, IN_PROGRESS, COMPLETED, ABANDONED
    }
}
