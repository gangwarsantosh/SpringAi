package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageSender sender;

    private String audioUrl;

    @OneToOne(cascade = CascadeType.ALL)
    private GrammarCorrection grammarCorrection;

    @OneToOne(cascade = CascadeType.ALL)
    private PronunciationFeedback pronunciationFeedback;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    private Date timestamp = new Date();

    public enum MessageSender {
        USER, AI, SYSTEM
    }

    public enum Sentiment {
        POSITIVE, NEUTRAL, NEGATIVE
    }
}
