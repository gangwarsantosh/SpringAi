package com.springai.springai.repository;

import com.springai.springai.model.SpeakingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeakingSessionRepository extends JpaRepository<SpeakingSession, Long> {
}
