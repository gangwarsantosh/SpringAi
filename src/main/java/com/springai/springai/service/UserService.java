package com.springai.springai.service;

import com.springai.springai.model.User;
import com.springai.springai.model.Achievement;
import com.springai.springai.repository.UserRepository;
import com.springai.springai.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User updateUserProfile(Long userId, User updatedUser) {
        User user = userRepository.findById(userId).orElseThrow();
        
        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getAvatar() != null) user.setAvatar(updatedUser.getAvatar());
        if (updatedUser.getLevel() != null) user.setLevel(updatedUser.getLevel());
        
        return userRepository.save(user);
    }

    public List<Achievement> getUserAchievements(Long userId) {
        return achievementRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
