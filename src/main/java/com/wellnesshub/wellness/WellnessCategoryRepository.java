package com.wellnesshub.wellness;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WellnessCategoryRepository extends JpaRepository<WellnessCategory, Long> {
    
    // Basic findBy methods
    Optional<WellnessCategory> findByName(String name);
    List<WellnessCategory> findByNameContainingIgnoreCase(String name);
    List<WellnessCategory> findByIsActive(Boolean isActive);
    
    // existsBy methods
    boolean existsByName(String name);
    boolean existsByIsActive(Boolean isActive);
    
    // Sorting and Pagination
    Page<WellnessCategory> findAll(Pageable pageable);
    List<WellnessCategory> findAll(Sort sort);
    Page<WellnessCategory> findByIsActive(Boolean isActive, Pageable pageable);
    Page<WellnessCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom queries
    @Query("SELECT c FROM WellnessCategory c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<WellnessCategory> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT c FROM WellnessCategory c WHERE c.isActive = true ORDER BY c.name ASC")
    List<WellnessCategory> findActiveCategoriesOrderByNameAsc();
    
    // Count queries
    long countByIsActive(Boolean isActive);
    long countByNameContainingIgnoreCase(String name);
}
