package com.wellnesshub.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #userId)")
    public ResponseEntity<UserProfile> get(@PathVariable Long userId) {
        Optional<UserProfile> profile = userProfileService.getProfileByUserId(userId);
        return profile.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #userId)")
    public ResponseEntity<UserProfile> upsert(@PathVariable Long userId, @RequestBody @Valid UserProfile profile) {
        UserProfile saved = userProfileService.createOrUpdateProfile(userId, profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #userId)")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        boolean deleted = userProfileService.deleteProfile(userId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
