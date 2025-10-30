package com.wellnesshub.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    
    // Basic findBy methods
    Optional<Province> findByCode(String code);
    Optional<Province> findByName(String name);
    List<Province> findByNameContainingIgnoreCase(String name);
    
    // existsBy methods
    boolean existsByCode(String code);
    boolean existsByName(String name);
    
    // Sorting and Pagination
    Page<Province> findAll(Pageable pageable);
    List<Province> findAll(Sort sort);
    Page<Province> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom queries
    @Query("SELECT p FROM Province p WHERE p.name LIKE %:keyword% OR p.code LIKE %:keyword%")
    List<Province> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Province p ORDER BY p.name ASC")
    List<Province> findAllOrderByNameAsc();
    
    // Count queries
    long countByNameContainingIgnoreCase(String name);
}
