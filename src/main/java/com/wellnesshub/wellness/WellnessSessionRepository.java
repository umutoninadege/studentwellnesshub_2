package com.wellnesshub.wellness;

import com.wellnesshub.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WellnessSessionRepository extends JpaRepository<WellnessSession, Long> {
    
    // Basic findBy methods
    List<WellnessSession> findByUser(User user);
    List<WellnessSession> findByUserId(Long userId);
    List<WellnessSession> findByResource(WellnessResource resource);
    List<WellnessSession> findByResourceId(Long resourceId);
    List<WellnessSession> findByProgressPercentage(Integer progressPercentage);
    List<WellnessSession> findByCompletedAtIsNotNull();
    List<WellnessSession> findByCompletedAtIsNull();
    
    // existsBy methods
    boolean existsByUser(User user);
    boolean existsByResource(WellnessResource resource);
    boolean existsByUserAndResource(User user, WellnessResource resource);
    
    // Sorting and Pagination
    Page<WellnessSession> findAll(Pageable pageable);
    List<WellnessSession> findAll(Sort sort);
    Page<WellnessSession> findByUser(User user, Pageable pageable);
    Page<WellnessSession> findByResource(WellnessResource resource, Pageable pageable);
    Page<WellnessSession> findByUserId(Long userId, Pageable pageable);
    Page<WellnessSession> findByResourceId(Long resourceId, Pageable pageable);
    
    // Custom queries
    @Query("SELECT s FROM WellnessSession s WHERE s.user.id = :userId AND s.completedAt IS NOT NULL ORDER BY s.completedAt DESC")
    List<WellnessSession> findCompletedSessionsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT s FROM WellnessSession s WHERE s.user.id = :userId AND s.completedAt IS NULL ORDER BY s.startedAt DESC")
    List<WellnessSession> findActiveSessionsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT s FROM WellnessSession s WHERE s.startedAt >= :fromDate AND s.startedAt <= :toDate")
    List<WellnessSession> findByDateRange(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);
    
    @Query("SELECT s FROM WellnessSession s WHERE s.progressPercentage >= :minProgress ORDER BY s.progressPercentage DESC")
    List<WellnessSession> findByMinProgress(@Param("minProgress") Integer minProgress);
    
    @Query("SELECT s FROM WellnessSession s WHERE s.user.id = :userId AND s.resource.category.id = :categoryId")
    List<WellnessSession> findByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);
    
    // Count queries
    long countByUser(User user);
    long countByResource(WellnessResource resource);
    long countByUserId(Long userId);
    long countByCompletedAtIsNotNull();
    long countByCompletedAtIsNull();
    long countByUserAndCompletedAtIsNotNull(User user);
}
