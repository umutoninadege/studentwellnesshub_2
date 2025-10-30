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
@RequestMapping("/api/wellness-resources")
public class WellnessResourceController {

    private final WellnessResourceService wellnessResourceService;

    public WellnessResourceController(WellnessResourceService wellnessResourceService) {
        this.wellnessResourceService = wellnessResourceService;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<WellnessResource> createResource(@RequestBody @Valid WellnessResource resource) {
        WellnessResource createdResource = wellnessResourceService.createResource(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    // READ - Get all resources
    @GetMapping
    public ResponseEntity<Page<WellnessResource>> getAllResources(Pageable pageable) {
        Page<WellnessResource> resources = wellnessResourceService.getAllResources(pageable);
        return ResponseEntity.ok(resources);
    }

    // READ - Get all resources (sorted)
    @GetMapping("/sorted")
    public ResponseEntity<List<WellnessResource>> getAllResourcesSorted(@RequestParam(defaultValue = "title") String sortBy,
                                                                        @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<WellnessResource> resources = wellnessResourceService.getAllResourcesSorted(sort);
        return ResponseEntity.ok(resources);
    }

    // READ - Get active resources only
    @GetMapping("/active")
    public ResponseEntity<Page<WellnessResource>> getActiveResources(Pageable pageable) {
        Page<WellnessResource> resources = wellnessResourceService.getActiveResources(pageable);
        return ResponseEntity.ok(resources);
    }

    // READ - Get resource by ID
    @GetMapping("/{id}")
    public ResponseEntity<WellnessResource> getResourceById(@PathVariable Long id) {
        Optional<WellnessResource> resource = wellnessResourceService.getResourceById(id);
        return resource.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // READ - Get resources by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<WellnessResource>> getResourcesByCategory(@PathVariable Long categoryId, Pageable pageable) {
        Page<WellnessResource> resources = wellnessResourceService.getResourcesByCategory(categoryId, pageable);
        return ResponseEntity.ok(resources);
    }

    // READ - Get active resources by category
    @GetMapping("/category/{categoryId}/active")
    public ResponseEntity<List<WellnessResource>> getActiveResourcesByCategory(@PathVariable Long categoryId) {
        List<WellnessResource> resources = wellnessResourceService.getActiveResourcesByCategory(categoryId);
        return ResponseEntity.ok(resources);
    }

    // READ - Get resources by type
    @GetMapping("/type/{resourceType}")
    public ResponseEntity<Page<WellnessResource>> getResourcesByType(@PathVariable String resourceType, Pageable pageable) {
        Page<WellnessResource> resources = wellnessResourceService.getResourcesByType(resourceType, pageable);
        return ResponseEntity.ok(resources);
    }

    // READ - Search resources by title
    @GetMapping("/search")
    public ResponseEntity<Page<WellnessResource>> searchResourcesByTitle(@RequestParam String title, Pageable pageable) {
        Page<WellnessResource> resources = wellnessResourceService.searchResourcesByTitle(title, pageable);
        return ResponseEntity.ok(resources);
    }

    // READ - Search resources by keyword
    @GetMapping("/search/keyword")
    public ResponseEntity<List<WellnessResource>> searchResourcesByKeyword(@RequestParam String keyword) {
        List<WellnessResource> resources = wellnessResourceService.searchResourcesByKeyword(keyword);
        return ResponseEntity.ok(resources);
    }

    // READ - Get resources by duration range
    @GetMapping("/duration")
    public ResponseEntity<List<WellnessResource>> getResourcesByDurationRange(@RequestParam Integer minDuration, 
                                                                              @RequestParam Integer maxDuration) {
        List<WellnessResource> resources = wellnessResourceService.getResourcesByDurationRange(minDuration, maxDuration);
        return ResponseEntity.ok(resources);
    }

    // READ - Get resources created after date
    @GetMapping("/created-after")
    public ResponseEntity<List<WellnessResource>> getResourcesCreatedAfter(@RequestParam String date) {
        LocalDateTime fromDate = LocalDateTime.parse(date);
        List<WellnessResource> resources = wellnessResourceService.getResourcesCreatedAfter(fromDate);
        return ResponseEntity.ok(resources);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<WellnessResource> updateResource(@PathVariable Long id, @RequestBody @Valid WellnessResource resource) {
        Optional<WellnessResource> updatedResource = wellnessResourceService.updateResource(id, resource);
        return updatedResource.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        boolean deleted = wellnessResourceService.deleteResource(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Additional endpoints
    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<WellnessSession>> getSessionsByResourceId(@PathVariable Long id) {
        List<WellnessSession> sessions = wellnessResourceService.getSessionsByResourceId(id);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getResourceCount() {
        long count = wellnessResourceService.getResourceCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveResourceCount() {
        long count = wellnessResourceService.getActiveResourceCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/category/{categoryId}")
    public ResponseEntity<Long> getResourceCountByCategory(@PathVariable Long categoryId) {
        long count = wellnessResourceService.getResourceCountByCategory(categoryId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/type/{resourceType}")
    public ResponseEntity<Long> getResourceCountByType(@PathVariable String resourceType) {
        long count = wellnessResourceService.getResourceCountByType(resourceType);
        return ResponseEntity.ok(count);
    }
}

