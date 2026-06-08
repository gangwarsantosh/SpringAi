package com.springai.springai.service;

import com.springai.springai.model.User;
import com.springai.springai.model.VocabularyWord;
import com.springai.springai.repository.UserRepository;
import com.springai.springai.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<VocabularyWord> getVocabularyList() {
        return vocabularyRepository.findAll();
    }

    public List<VocabularyWord> searchVocabulary(String query) {
        return vocabularyRepository.findByWordContainingIgnoreCase(query);
    }

    public void saveWordForUser(Long userId, Long wordId) {
        User user = userRepository.findById(userId).orElseThrow();
        VocabularyWord word = vocabularyRepository.findById(wordId).orElseThrow();
        
        if (!word.getUsers().contains(user)) {
            word.getUsers().add(user);
            vocabularyRepository.save(word);
        }
    }

    public void removeWordForUser(Long userId, Long wordId) {
        User user = userRepository.findById(userId).orElseThrow();
        VocabularyWord word = vocabularyRepository.findById(wordId).orElseThrow();
        
        word.getUsers().remove(user);
        vocabularyRepository.save(word);
    }
}
