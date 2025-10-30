package com.wellnesshub.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VillageRepository extends JpaRepository<Village, Long> {
    
    // Basic findBy methods
    Optional<Village> findByCode(String code);
    Optional<Village> findByName(String name);
    List<Village> findByNameContainingIgnoreCase(String name);
    List<Village> findByCell(Cell cell);
    List<Village> findByCellId(Long cellId);
    
    // existsBy methods
    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsByCell(Cell cell);
    
    // Sorting and Pagination
    Page<Village> findAll(Pageable pageable);
    List<Village> findAll(Sort sort);
    Page<Village> findByCell(Cell cell, Pageable pageable);
    Page<Village> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom queries
    @Query("SELECT v FROM Village v WHERE v.cell.id = :cellId ORDER BY v.name ASC")
    List<Village> findByCellIdOrderByNameAsc(@Param("cellId") Long cellId);
    
    @Query("SELECT v FROM Village v WHERE v.name LIKE %:keyword% OR v.code LIKE %:keyword%")
    List<Village> findByKeyword(@Param("keyword") String keyword);
    
    // Count queries
    long countByCell(Cell cell);
    long countByNameContainingIgnoreCase(String name);
}
