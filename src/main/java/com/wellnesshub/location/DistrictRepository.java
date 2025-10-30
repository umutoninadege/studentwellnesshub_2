package com.wellnesshub.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    
    // Basic findBy methods
    Optional<District> findByCode(String code);
    Optional<District> findByName(String name);
    List<District> findByNameContainingIgnoreCase(String name);
    List<District> findByProvince(Province province);
    List<District> findByProvinceId(Long provinceId);
    
    // existsBy methods
    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsByProvince(Province province);
    
    // Sorting and Pagination
    Page<District> findAll(Pageable pageable);
    List<District> findAll(Sort sort);
    Page<District> findByProvince(Province province, Pageable pageable);
    Page<District> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom queries
    @Query("SELECT d FROM District d WHERE d.province.id = :provinceId ORDER BY d.name ASC")
    List<District> findByProvinceIdOrderByNameAsc(@Param("provinceId") Long provinceId);
    
    @Query("SELECT d FROM District d WHERE d.name LIKE %:keyword% OR d.code LIKE %:keyword%")
    List<District> findByKeyword(@Param("keyword") String keyword);
    
    // Count queries
    long countByProvince(Province province);
    long countByNameContainingIgnoreCase(String name);
}
