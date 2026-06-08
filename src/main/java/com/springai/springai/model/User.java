package com.springai.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private UserLevel level = UserLevel.BEGINNER;

    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "user_achievement",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> achievements = new ArrayList<>();

    public enum UserLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, PROFESSIONAL
    }
}
