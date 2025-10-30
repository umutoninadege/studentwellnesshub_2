package com.wellnesshub.user;

import com.wellnesshub.location.Village;
import com.wellnesshub.wellness.WellnessCategory;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@Email
	@NotBlank
	private String email;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 8)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String passwordHash;

	@Column(nullable = false)
	@NotBlank
	private String fullName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	private boolean active = true;

	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "village_id")
	private Village village;

	// One-to-One Profile
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private UserProfile profile;

	// Explicit Many-to-Many example: user's favorite wellness categories
	@ManyToMany
	@JoinTable(
		name = "user_favorite_categories",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private Set<WellnessCategory> favoriteCategories = new HashSet<>();

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() { return createdAt; }

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public UserProfile getProfile() { return profile; }
	public void setProfile(UserProfile profile) {
		this.profile = profile;
		if (profile != null) {
			profile.setUser(this);
		}
	}

	public Set<WellnessCategory> getFavoriteCategories() { return favoriteCategories; }
	public void setFavoriteCategories(Set<WellnessCategory> favoriteCategories) { this.favoriteCategories = favoriteCategories; }

	public void addFavoriteCategory(WellnessCategory category) { this.favoriteCategories.add(category); }
	public void removeFavoriteCategory(WellnessCategory category) { this.favoriteCategories.remove(category); }
}





