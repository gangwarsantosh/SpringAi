package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grammar_corrections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrammarCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String original;

    @Column(columnDefinition = "TEXT")
    private String corrected;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    private String rule;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ElementCollection
    private List<String> examples = new ArrayList<>();

    private String errorType;

    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
