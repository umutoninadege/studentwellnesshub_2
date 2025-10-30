package com.wellnesshub.wellness;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wellness-sessions")
public class WellnessSessionController {

    private final WellnessSessionService wellnessSessionService;

    public WellnessSessionController(WellnessSessionService wellnessSessionService) {
        this.wellnessSessionService = wellnessSessionService;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('COUNSELOR')")
    public ResponseEntity<WellnessSession> createSession(@RequestBody @Valid WellnessSession session) {
        WellnessSession createdSession = wellnessSessionService.createSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    // READ - Get all sessions
    @GetMapping
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<WellnessSession>> getAllSessions(Pageable pageable) {
        Page<WellnessSession> sessions = wellnessSessionService.getAllSessions(pageable);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get all sessions (sorted)
    @GetMapping("/sorted")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<WellnessSession>> getAllSessionsSorted(@RequestParam(defaultValue = "startedAt") String sortBy,
                                                                      @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<WellnessSession> sessions = wellnessSessionService.getAllSessionsSorted(sort);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get session by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwner(authentication.name, #id)")
    public ResponseEntity<WellnessSession> getSessionById(@PathVariable Long id) {
        Optional<WellnessSession> session = wellnessSessionService.getSessionById(id);
        return session.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // READ - Get sessions by user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwnerByUserId(authentication.name, #userId)")
    public ResponseEntity<Page<WellnessSession>> getSessionsByUser(@PathVariable Long userId, Pageable pageable) {
        Page<WellnessSession> sessions = wellnessSessionService.getSessionsByUser(userId, pageable);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get completed sessions by user
    @GetMapping("/user/{userId}/completed")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwnerByUserId(authentication.name, #userId)")
    public ResponseEntity<List<WellnessSession>> getCompletedSessionsByUser(@PathVariable Long userId) {
        List<WellnessSession> sessions = wellnessSessionService.getCompletedSessionsByUser(userId);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get active sessions by user
    @GetMapping("/user/{userId}/active")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwnerByUserId(authentication.name, #userId)")
    public ResponseEntity<List<WellnessSession>> getActiveSessionsByUser(@PathVariable Long userId) {
        List<WellnessSession> sessions = wellnessSessionService.getActiveSessionsByUser(userId);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get sessions by resource
    @GetMapping("/resource/{resourceId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<WellnessSession>> getSessionsByResource(@PathVariable Long resourceId, Pageable pageable) {
        Page<WellnessSession> sessions = wellnessSessionService.getSessionsByResource(resourceId, pageable);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get sessions by date range
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<WellnessSession>> getSessionsByDateRange(@RequestParam String fromDate, 
                                                                        @RequestParam String toDate) {
        LocalDateTime from = LocalDateTime.parse(fromDate);
        LocalDateTime to = LocalDateTime.parse(toDate);
        List<WellnessSession> sessions = wellnessSessionService.getSessionsByDateRange(from, to);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get sessions by minimum progress
    @GetMapping("/progress")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<WellnessSession>> getSessionsByMinProgress(@RequestParam Integer minProgress) {
        List<WellnessSession> sessions = wellnessSessionService.getSessionsByMinProgress(minProgress);
        return ResponseEntity.ok(sessions);
    }

    // READ - Get sessions by user and category
    @GetMapping("/user/{userId}/category/{categoryId}")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwnerByUserId(authentication.name, #userId)")
    public ResponseEntity<List<WellnessSession>> getSessionsByUserAndCategory(@PathVariable Long userId, 
                                                                              @PathVariable Long categoryId) {
        List<WellnessSession> sessions = wellnessSessionService.getSessionsByUserAndCategory(userId, categoryId);
        return ResponseEntity.ok(sessions);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwner(authentication.name, #id)")
    public ResponseEntity<WellnessSession> updateSession(@PathVariable Long id, @RequestBody @Valid WellnessSession session) {
        Optional<WellnessSession> updatedSession = wellnessSessionService.updateSession(id, session);
        return updatedSession.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE - Mark session as completed
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwner(authentication.name, #id)")
    public ResponseEntity<WellnessSession> completeSession(@PathVariable Long id) {
        Optional<WellnessSession> completedSession = wellnessSessionService.completeSession(id);
        return completedSession.map(ResponseEntity::ok)
                              .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE - Update progress
    @PutMapping("/{id}/progress")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwner(authentication.name, #id)")
    public ResponseEntity<WellnessSession> updateProgress(@PathVariable Long id, @RequestParam Integer progress) {
        Optional<WellnessSession> updatedSession = wellnessSessionService.updateProgress(id, progress);
        return updatedSession.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwner(authentication.name, #id)")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        boolean deleted = wellnessSessionService.deleteSession(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Additional endpoints
    @GetMapping("/count")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Long> getSessionCount() {
        long count = wellnessSessionService.getSessionCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/user/{userId}")
    @PreAuthorize("hasRole('COUNSELOR') or @wellnessSessionService.isOwnerByUserId(authentication.name, #userId)")
    public ResponseEntity<Long> getSessionCountByUser(@PathVariable Long userId) {
        long count = wellnessSessionService.getSessionCountByUser(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/completed")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Long> getCompletedSessionCount() {
        long count = wellnessSessionService.getCompletedSessionCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/active")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Long> getActiveSessionCount() {
        long count = wellnessSessionService.getActiveSessionCount();
        return ResponseEntity.ok(count);
    }
}

