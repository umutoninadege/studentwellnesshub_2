package com.wellnesshub.wellness;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wellness-categories")
public class WellnessCategoryController {

    private final WellnessCategoryService wellnessCategoryService;

    public WellnessCategoryController(WellnessCategoryService wellnessCategoryService) {
        this.wellnessCategoryService = wellnessCategoryService;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<WellnessCategory> createCategory(@RequestBody @Valid WellnessCategory category) {
        WellnessCategory createdCategory = wellnessCategoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // READ - Get all categories
    @GetMapping
    public ResponseEntity<Page<WellnessCategory>> getAllCategories(Pageable pageable) {
        Page<WellnessCategory> categories = wellnessCategoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    // READ - Get all categories (sorted)
    @GetMapping("/sorted")
    public ResponseEntity<List<WellnessCategory>> getAllCategoriesSorted(@RequestParam(defaultValue = "name") String sortBy,
                                                                         @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<WellnessCategory> categories = wellnessCategoryService.getAllCategoriesSorted(sort);
        return ResponseEntity.ok(categories);
    }

    // READ - Get active categories only
    @GetMapping("/active")
    public ResponseEntity<List<WellnessCategory>> getActiveCategories() {
        List<WellnessCategory> categories = wellnessCategoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    // READ - Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<WellnessCategory> getCategoryById(@PathVariable Long id) {
        Optional<WellnessCategory> category = wellnessCategoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // READ - Get category by name
    @GetMapping("/name/{name}")
    public ResponseEntity<WellnessCategory> getCategoryByName(@PathVariable String name) {
        Optional<WellnessCategory> category = wellnessCategoryService.getCategoryByName(name);
        return category.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // READ - Search categories by name
    @GetMapping("/search")
    public ResponseEntity<Page<WellnessCategory>> searchCategoriesByName(@RequestParam String name, Pageable pageable) {
        Page<WellnessCategory> categories = wellnessCategoryService.searchCategoriesByName(name, pageable);
        return ResponseEntity.ok(categories);
    }

    // READ - Search categories by keyword
    @GetMapping("/search/keyword")
    public ResponseEntity<List<WellnessCategory>> searchCategoriesByKeyword(@RequestParam String keyword) {
        List<WellnessCategory> categories = wellnessCategoryService.searchCategoriesByKeyword(keyword);
        return ResponseEntity.ok(categories);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<WellnessCategory> updateCategory(@PathVariable Long id, @RequestBody @Valid WellnessCategory category) {
        Optional<WellnessCategory> updatedCategory = wellnessCategoryService.updateCategory(id, category);
        return updatedCategory.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = wellnessCategoryService.deleteCategory(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Additional endpoints
    @GetMapping("/{id}/resources")
    public ResponseEntity<List<WellnessResource>> getResourcesByCategoryId(@PathVariable Long id) {
        List<WellnessResource> resources = wellnessCategoryService.getResourcesByCategoryId(id);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCategoryCount() {
        long count = wellnessCategoryService.getCategoryCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveCategoryCount() {
        long count = wellnessCategoryService.getActiveCategoryCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/search")
    public ResponseEntity<Long> getCategoryCountByName(@RequestParam String name) {
        long count = wellnessCategoryService.getCategoryCountByName(name);
        return ResponseEntity.ok(count);
    }
}

