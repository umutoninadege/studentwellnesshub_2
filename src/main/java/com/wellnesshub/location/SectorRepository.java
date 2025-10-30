package com.wellnesshub.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    
    // Basic findBy methods
    Optional<Sector> findByCode(String code);
    Optional<Sector> findByName(String name);
    List<Sector> findByNameContainingIgnoreCase(String name);
    List<Sector> findByDistrict(District district);
    List<Sector> findByDistrictId(Long districtId);
    
    // existsBy methods
    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsByDistrict(District district);
    
    // Sorting and Pagination
    Page<Sector> findAll(Pageable pageable);
    List<Sector> findAll(Sort sort);
    Page<Sector> findByDistrict(District district, Pageable pageable);
    Page<Sector> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom queries
    @Query("SELECT s FROM Sector s WHERE s.district.id = :districtId ORDER BY s.name ASC")
    List<Sector> findByDistrictIdOrderByNameAsc(@Param("districtId") Long districtId);
    
    @Query("SELECT s FROM Sector s WHERE s.name LIKE %:keyword% OR s.code LIKE %:keyword%")
    List<Sector> findByKeyword(@Param("keyword") String keyword);
    
    // Count queries
    long countByDistrict(District district);
    long countByNameContainingIgnoreCase(String name);
}
