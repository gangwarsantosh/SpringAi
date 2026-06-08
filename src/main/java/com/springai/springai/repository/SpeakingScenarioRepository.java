package com.springai.springai.repository;

import com.springai.springai.model.SpeakingScenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpeakingScenarioRepository extends JpaRepository<SpeakingScenario, Long> {
    List<SpeakingScenario> findByDifficulty(SpeakingScenario.Difficulty difficulty);
}
