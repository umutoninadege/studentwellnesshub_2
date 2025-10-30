package com.wellnesshub.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createOrUpdateProfile(Long userId, UserProfile profile) {
        User user = userRepository.findById(userId).orElseThrow();
        if (user.getProfile() != null) {
            UserProfile existing = user.getProfile();
            existing.setBio(profile.getBio());
            existing.setPhone(profile.getPhone());
            return userProfileRepository.save(existing);
        }
        profile.setUser(user);
        UserProfile saved = userProfileRepository.save(profile);
        user.setProfile(saved);
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<UserProfile> getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }

    public boolean deleteProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .map(p -> { userProfileRepository.delete(p); return true; })
                .orElse(false);
    }
}
