package com.springai.springai.repository;

import com.springai.springai.model.VocabularyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<VocabularyWord, Long> {
    List<VocabularyWord> findByDifficulty(VocabularyWord.Difficulty difficulty);
    List<VocabularyWord> findByWordContainingIgnoreCase(String query);
}
