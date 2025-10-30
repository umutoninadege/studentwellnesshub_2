package com.wellnesshub.wellness;

import com.wellnesshub.user.User;
import com.wellnesshub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WellnessSessionService {

    private final WellnessSessionRepository wellnessSessionRepository;
    private final WellnessResourceRepository wellnessResourceRepository;
    private final UserRepository userRepository;

    public WellnessSessionService(WellnessSessionRepository wellnessSessionRepository,
                                WellnessResourceRepository wellnessResourceRepository,
                                UserRepository userRepository) {
        this.wellnessSessionRepository = wellnessSessionRepository;
        this.wellnessResourceRepository = wellnessResourceRepository;
        this.userRepository = userRepository;
    }

    public WellnessSession createSession(WellnessSession session) {
        if (userRepository.findById(session.getUser().getId()).isEmpty()) {
            throw new IllegalArgumentException("User with id " + session.getUser().getId() + " not found");
        }
        if (wellnessResourceRepository.findById(session.getResource().getId()).isEmpty()) {
            throw new IllegalArgumentException("Resource with id " + session.getResource().getId() + " not found");
        }
        if (wellnessSessionRepository.existsByUserAndResource(session.getUser(), session.getResource())) {
            throw new IllegalArgumentException("Session for this user and resource already exists");
        }
        
        session.setStartedAt(LocalDateTime.now());
        return wellnessSessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public Page<WellnessSession> getAllSessions(Pageable pageable) {
        return wellnessSessionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getAllSessionsSorted(Sort sort) {
        return wellnessSessionRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Optional<WellnessSession> getSessionById(Long id) {
        return wellnessSessionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<WellnessSession> getSessionsByUser(Long userId, Pageable pageable) {
        return wellnessSessionRepository.findByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getCompletedSessionsByUser(Long userId) {
        return wellnessSessionRepository.findCompletedSessionsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getActiveSessionsByUser(Long userId) {
        return wellnessSessionRepository.findActiveSessionsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Page<WellnessSession> getSessionsByResource(Long resourceId, Pageable pageable) {
        return wellnessSessionRepository.findByResourceId(resourceId, pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getSessionsByDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return wellnessSessionRepository.findByDateRange(fromDate, toDate);
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getSessionsByMinProgress(Integer minProgress) {
        return wellnessSessionRepository.findByMinProgress(minProgress);
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getSessionsByUserAndCategory(Long userId, Long categoryId) {
        return wellnessSessionRepository.findByUserIdAndCategoryId(userId, categoryId);
    }

    public Optional<WellnessSession> updateSession(Long id, WellnessSession session) {
        return wellnessSessionRepository.findById(id)
                .map(existingSession -> {
                    if (userRepository.findById(session.getUser().getId()).isEmpty()) {
                        throw new IllegalArgumentException("User with id " + session.getUser().getId() + " not found");
                    }
                    if (wellnessResourceRepository.findById(session.getResource().getId()).isEmpty()) {
                        throw new IllegalArgumentException("Resource with id " + session.getResource().getId() + " not found");
                    }
                    
                    existingSession.setUser(session.getUser());
                    existingSession.setResource(session.getResource());
                    existingSession.setProgressPercentage(session.getProgressPercentage());
                    existingSession.setNotes(session.getNotes());
                    return wellnessSessionRepository.save(existingSession);
                });
    }

    public Optional<WellnessSession> completeSession(Long id) {
        return wellnessSessionRepository.findById(id)
                .map(session -> {
                    session.markAsCompleted();
                    return wellnessSessionRepository.save(session);
                });
    }

    public Optional<WellnessSession> updateProgress(Long id, Integer progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100");
        }
        
        return wellnessSessionRepository.findById(id)
                .map(session -> {
                    session.setProgressPercentage(progress);
                    if (progress == 100) {
                        session.markAsCompleted();
                    }
                    return wellnessSessionRepository.save(session);
                });
    }

    public boolean deleteSession(Long id) {
        if (wellnessSessionRepository.existsById(id)) {
            wellnessSessionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public long getSessionCount() {
        return wellnessSessionRepository.count();
    }

    @Transactional(readOnly = true)
    public long getSessionCountByUser(Long userId) {
        return wellnessSessionRepository.countByUserId(userId);
    }

    @Transactional(readOnly = true)
    public long getCompletedSessionCount() {
        return wellnessSessionRepository.countByCompletedAtIsNotNull();
    }

    @Transactional(readOnly = true)
    public long getActiveSessionCount() {
        return wellnessSessionRepository.countByCompletedAtIsNull();
    }

    // Security helper methods
    public boolean isOwner(String email, Long sessionId) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return false;
        
        Optional<WellnessSession> session = wellnessSessionRepository.findById(sessionId);
        return session.map(s -> s.getUser().getId().equals(user.get().getId())).orElse(false);
    }

    public boolean isOwnerByUserId(String email, Long userId) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(u -> u.getId().equals(userId)).orElse(false);
    }
}
