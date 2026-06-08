package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vocabulary_words")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(columnDefinition = "TEXT")
    private String meaning;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ElementCollection
    private List<String> exampleSentences = new ArrayList<>();

    private String pronunciation;

    private String partOfSpeech;

    @ManyToMany
    @JoinTable(
            name = "user_vocabulary",
            joinColumns = @JoinColumn(name = "vocabulary_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    private Date createdAt = new Date();

    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
