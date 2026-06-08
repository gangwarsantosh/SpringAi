package com.springai.springai.repository;

import com.springai.springai.model.SkillStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkillStatsRepository extends JpaRepository<SkillStats, Long> {
    List<SkillStats> findByUserId(Long userId);
}
