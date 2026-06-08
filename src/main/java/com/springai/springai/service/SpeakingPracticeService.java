package com.springai.springai.service;

import com.springai.springai.model.User;
import com.springai.springai.model.SpeakingScenario;
import com.springai.springai.model.SpeakingSession;
import com.springai.springai.repository.UserRepository;
import com.springai.springai.repository.SpeakingScenarioRepository;
import com.springai.springai.repository.SpeakingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpeakingPracticeService {
    @Autowired
    private SpeakingScenarioRepository speakingScenarioRepository;

    @Autowired
    private SpeakingSessionRepository speakingSessionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SpeakingScenario> getSpeakingScenarios(String difficulty) {
        return speakingScenarioRepository.findByDifficulty(
            SpeakingScenario.Difficulty.valueOf(difficulty.toUpperCase())
        );
    }

    public SpeakingSession startSession(Long userId, Long scenarioId) {
        User user = userRepository.findById(userId).orElseThrow();
        SpeakingScenario scenario = speakingScenarioRepository.findById(scenarioId).orElseThrow();
        
        SpeakingSession session = new SpeakingSession();
        session.setUser(user);
        session.setScenario(scenario);
        session.setStatus(SpeakingSession.SessionStatus.STARTED);
        session.setStartTime(LocalDateTime.now());
        
        return speakingSessionRepository.save(session);
    }

    public void endSession(Long sessionId) {
        SpeakingSession session = speakingSessionRepository.findById(sessionId).orElseThrow();
        session.setStatus(SpeakingSession.SessionStatus.COMPLETED);
        session.setEndTime(LocalDateTime.now());
        speakingSessionRepository.save(session);
    }
}
