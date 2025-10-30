package com.wellnesshub.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CellRepository extends JpaRepository<Cell, Long> {
    
    // Basic findBy methods
    Optional<Cell> findByCode(String code);
    Optional<Cell> findByName(String name);
    List<Cell> findByNameContainingIgnoreCase(String name);
    List<Cell> findBySector(Sector sector);
    List<Cell> findBySectorId(Long sectorId);
    
    // existsBy methods
    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsBySector(Sector sector);
    
    // Sorting and Pagination
    Page<Cell> findAll(Pageable pageable);
    List<Cell> findAll(Sort sort);
    Page<Cell> findBySector(Sector sector, Pageable pageable);
    Page<Cell> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom queries
    @Query("SELECT c FROM Cell c WHERE c.sector.id = :sectorId ORDER BY c.name ASC")
    List<Cell> findBySectorIdOrderByNameAsc(@Param("sectorId") Long sectorId);
    
    @Query("SELECT c FROM Cell c WHERE c.name LIKE %:keyword% OR c.code LIKE %:keyword%")
    List<Cell> findByKeyword(@Param("keyword") String keyword);
    
    // Count queries
    long countBySector(Sector sector);
    long countByNameContainingIgnoreCase(String name);
}
