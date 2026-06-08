package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skill_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Skill skillName;

    private Double score;

    private String level;

    @Enumerated(EnumType.STRING)
    private Trend trend;

    private Double improvement;

    public enum Skill {
        GRAMMAR, FLUENCY, VOCABULARY, PRONUNCIATION
    }

    public enum Trend {
        IMPROVING, STABLE, DECLINING
    }
}
