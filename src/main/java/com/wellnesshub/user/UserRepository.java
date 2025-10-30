package com.wellnesshub.user;

import com.wellnesshub.location.Province;
import com.wellnesshub.location.District;
import com.wellnesshub.location.Sector;
import com.wellnesshub.location.Cell;
import com.wellnesshub.location.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	// Basic findBy methods
	Optional<User> findByEmail(String email);
	List<User> findByFullNameContainingIgnoreCase(String fullName);
	Page<User> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
	List<User> findByRole(Role role);
	List<User> findByActive(Boolean active);
	List<User> findByVillage(Village village);
	List<User> findByVillageId(Long villageId);

	// favorites-related query removed
	
	// existsBy methods
	boolean existsByEmail(String email);
	boolean existsByFullName(String fullName);
	boolean existsByRole(Role role);
	boolean existsByVillage(Village village);
	
	// Sorting and Pagination
	Page<User> findAll(Pageable pageable);
	List<User> findAll(Sort sort);
	Page<User> findByRole(Role role, Pageable pageable);
	Page<User> findByActive(Boolean active, Pageable pageable);
	Page<User> findByVillage(Village village, Pageable pageable);
	
	// Location-based queries
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province = :province")
	List<User> findByProvince(@Param("province") Province province);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.id = :provinceId")
	List<User> findByProvinceId(@Param("provinceId") Long provinceId);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.code = :provinceCode")
	List<User> findByProvinceCode(@Param("provinceCode") String provinceCode);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.name = :provinceName")
	List<User> findByProvinceName(@Param("provinceName") String provinceName);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district = :district")
	List<User> findByDistrict(@Param("district") District district);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector = :sector")
	List<User> findBySector(@Param("sector") Sector sector);
	
	@Query("SELECT u FROM User u WHERE u.village.cell = :cell")
	List<User> findByCell(@Param("cell") Cell cell);
	
	// Custom queries with pagination
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province = :province")
	Page<User> findByProvince(@Param("province") Province province, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.id = :provinceId")
	Page<User> findByProvinceId(@Param("provinceId") Long provinceId, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.role = :role AND u.village.cell.sector.district.province = :province")
	List<User> findByRoleAndProvince(@Param("role") Role role, @Param("province") Province province);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.id = :districtId")
	List<User> findByDistrictId(@Param("districtId") Long districtId);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.sector.id = :sectorId")
	List<User> findBySectorId(@Param("sectorId") Long sectorId);
	
	@Query("SELECT u FROM User u WHERE u.village.cell.id = :cellId")
	List<User> findByCellId(@Param("cellId") Long cellId);
	
	// Count queries
	long countByRole(Role role);
	long countByActive(Boolean active);
	long countByVillage(Village village);
	
	@Query("SELECT COUNT(u) FROM User u WHERE u.village.cell.sector.district.province = :province")
	long countByProvince(@Param("province") Province province);
	
	@Query("SELECT COUNT(u) FROM User u WHERE u.village.cell.sector.district = :district")
	long countByDistrict(@Param("district") District district);
	
	@Query("SELECT COUNT(u) FROM User u WHERE u.village.cell.sector = :sector")
	long countBySector(@Param("sector") Sector sector);    // Additional count method for Cell
    @Query("SELECT COUNT(u) FROM User u WHERE u.village.cell = :cell")
    long countByCell(@Param("cell") Cell cell);
}



