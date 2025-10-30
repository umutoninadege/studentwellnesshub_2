package com.wellnesshub.wellness;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellnesshub.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "wellness_categories")
public class WellnessCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    
    @Column(length = 200)
    @Size(max = 200)
    private String description;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WellnessResource> resources = new ArrayList<>();

    // Reverse side of explicit Many-to-Many with User favorites
    @ManyToMany(mappedBy = "favoriteCategories")
    @JsonIgnore
    private Set<User> followers = new HashSet<>();
    
    // Constructors
    public WellnessCategory() {}
    
    public WellnessCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public List<WellnessResource> getResources() { return resources; }
    public void setResources(List<WellnessResource> resources) { this.resources = resources; }

    public Set<User> getFollowers() { return followers; }
    public void setFollowers(Set<User> followers) { this.followers = followers; }
    
    // Helper methods
    public void addResource(WellnessResource resource) {
        resources.add(resource);
        resource.setCategory(this);
    }
    
    public void removeResource(WellnessResource resource) {
        resources.remove(resource);
        resource.setCategory(null);
    }
}
