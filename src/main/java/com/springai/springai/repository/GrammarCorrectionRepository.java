package com.springai.springai.repository;

import com.springai.springai.model.GrammarCorrection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarCorrectionRepository extends JpaRepository<GrammarCorrection, Long> {
}
