package com.springai.springai.repository;

import com.springai.springai.model.DailyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyStatsRepository extends JpaRepository<DailyStats, Long> {
    Optional<DailyStats> findByUserIdAndDate(Long userId, LocalDate date);
    List<DailyStats> findByUserIdOrderByDateDesc(Long userId);
}
