package com.wellnesshub.wellness;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WellnessResourceRepository extends JpaRepository<WellnessResource, Long> {
    
    // Basic findBy methods
    List<WellnessResource> findByTitleContainingIgnoreCase(String title);
    List<WellnessResource> findByResourceType(String resourceType);
    List<WellnessResource> findByIsActive(Boolean isActive);
    List<WellnessResource> findByCategory(WellnessCategory category);
    List<WellnessResource> findByCategoryId(Long categoryId);
    List<WellnessResource> findByDurationMinutes(Integer durationMinutes);
    
    // existsBy methods
    boolean existsByTitle(String title);
    boolean existsByResourceType(String resourceType);
    boolean existsByCategory(WellnessCategory category);
    
    // Sorting and Pagination
    Page<WellnessResource> findAll(Pageable pageable);
    List<WellnessResource> findAll(Sort sort);
    Page<WellnessResource> findByIsActive(Boolean isActive, Pageable pageable);
    Page<WellnessResource> findByCategory(WellnessCategory category, Pageable pageable);
    Page<WellnessResource> findByResourceType(String resourceType, Pageable pageable);
    Page<WellnessResource> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // Custom queries
    @Query("SELECT r FROM WellnessResource r WHERE r.title LIKE %:keyword% OR r.description LIKE %:keyword%")
    List<WellnessResource> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT r FROM WellnessResource r WHERE r.isActive = true AND r.category.id = :categoryId ORDER BY r.title ASC")
    List<WellnessResource> findActiveByCategoryIdOrderByTitleAsc(@Param("categoryId") Long categoryId);
    
    @Query("SELECT r FROM WellnessResource r WHERE r.createdAt >= :fromDate ORDER BY r.createdAt DESC")
    List<WellnessResource> findByCreatedAtAfter(@Param("fromDate") LocalDateTime fromDate);
    
    @Query("SELECT r FROM WellnessResource r WHERE r.durationMinutes BETWEEN :minDuration AND :maxDuration")
    List<WellnessResource> findByDurationRange(@Param("minDuration") Integer minDuration, @Param("maxDuration") Integer maxDuration);
    
    // Count queries
    long countByIsActive(Boolean isActive);
    long countByCategory(WellnessCategory category);
    long countByResourceType(String resourceType);
    long countByTitleContainingIgnoreCase(String title);
}
