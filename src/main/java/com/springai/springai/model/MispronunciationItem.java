package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mispronunciations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MispronunciationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private String incorrect;

    private String correct;
}
