package com.wellnesshub.wellness;

import com.wellnesshub.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "wellness_sessions")
public class WellnessSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    @NotNull
    private WellnessResource resource;
    
    @Column(nullable = false)
    @NotNull
    private LocalDateTime startedAt;
    
    private LocalDateTime completedAt;
    
    @Column(nullable = false)
    private Integer progressPercentage = 0;
    
    @Column(length = 500)
    private String notes;
    
    // Constructors
    public WellnessSession() {}
    
    public WellnessSession(User user, WellnessResource resource) {
        this.user = user;
        this.resource = resource;
        this.startedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public WellnessResource getResource() { return resource; }
    public void setResource(WellnessResource resource) { this.resource = resource; }
    
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    
    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    // Helper methods
    public boolean isCompleted() {
        return completedAt != null;
    }
    
    public void markAsCompleted() {
        this.completedAt = LocalDateTime.now();
        this.progressPercentage = 100;
    }
}
