package com.wellnesshub.wellness;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "wellness_resources")
public class WellnessResource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 200)
    private String title;
    
    @Column(nullable = false, length = 1000)
    @NotBlank
    @Size(min = 10, max = 1000)
    private String description;
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 50)
    private String resourceType; // ARTICLE, VIDEO, AUDIO, EXERCISE, etc.
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 500)
    private String contentUrl;
    
    @Column(nullable = false)
    @NotNull
    private Integer durationMinutes;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private WellnessCategory category;
    
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WellnessSession> sessions = new ArrayList<>();
    
    // Constructors
    public WellnessResource() {}
    
    public WellnessResource(String title, String description, String resourceType, 
                          String contentUrl, Integer durationMinutes, WellnessCategory category) {
        this.title = title;
        this.description = description;
        this.resourceType = resourceType;
        this.contentUrl = contentUrl;
        this.durationMinutes = durationMinutes;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    
    public String getContentUrl() { return contentUrl; }
    public void setContentUrl(String contentUrl) { this.contentUrl = contentUrl; }
    
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public WellnessCategory getCategory() { return category; }
    public void setCategory(WellnessCategory category) { this.category = category; }
    
    public List<WellnessSession> getSessions() { return sessions; }
    public void setSessions(List<WellnessSession> sessions) { this.sessions = sessions; }
    
    // Helper methods
    public void addSession(WellnessSession session) {
        sessions.add(session);
        session.setResource(this);
    }
    
    public void removeSession(WellnessSession session) {
        sessions.remove(session);
        session.setResource(null);
    }
}
