package com.wellnesshub.location;

import com.wellnesshub.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Province endpoints
    @GetMapping("/provinces")
    public ResponseEntity<Page<Province>> getAllProvinces(Pageable pageable) {
        Page<Province> provinces = locationService.getAllProvinces(pageable);
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/provinces/{provinceId}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByProvinceId(@PathVariable Long provinceId) {
        List<User> users = locationService.getUsersByProvinceId(provinceId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/provinces/code/{provinceCode}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByProvinceCode(@PathVariable String provinceCode) {
        List<User> users = locationService.getUsersByProvinceCode(provinceCode);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/provinces/name/{provinceName}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByProvinceName(@PathVariable String provinceName) {
        List<User> users = locationService.getUsersByProvinceName(provinceName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/provinces/{provinceId}/users/paginated")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<User>> getUsersByProvinceIdPaginated(@PathVariable Long provinceId, Pageable pageable) {
        Page<User> users = locationService.getUsersByProvinceIdPaginated(provinceId, pageable);
        return ResponseEntity.ok(users);
    }

    // District endpoints
    @GetMapping("/districts")
    public ResponseEntity<Page<District>> getAllDistricts(Pageable pageable) {
        Page<District> districts = locationService.getAllDistricts(pageable);
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/districts/{districtId}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByDistrictId(@PathVariable Long districtId) {
        List<User> users = locationService.getUsersByDistrictId(districtId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/districts/province/{provinceId}")
    public ResponseEntity<List<District>> getDistrictsByProvinceId(@PathVariable Long provinceId) {
        List<District> districts = locationService.getDistrictsByProvinceId(provinceId);
        return ResponseEntity.ok(districts);
    }

    // Sector endpoints
    @GetMapping("/sectors")
    public ResponseEntity<Page<Sector>> getAllSectors(Pageable pageable) {
        Page<Sector> sectors = locationService.getAllSectors(pageable);
        return ResponseEntity.ok(sectors);
    }

    @GetMapping("/sectors/{sectorId}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersBySectorId(@PathVariable Long sectorId) {
        List<User> users = locationService.getUsersBySectorId(sectorId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sectors/district/{districtId}")
    public ResponseEntity<List<Sector>> getSectorsByDistrictId(@PathVariable Long districtId) {
        List<Sector> sectors = locationService.getSectorsByDistrictId(districtId);
        return ResponseEntity.ok(sectors);
    }

    // Cell endpoints
    @GetMapping("/cells")
    public ResponseEntity<Page<Cell>> getAllCells(Pageable pageable) {
        Page<Cell> cells = locationService.getAllCells(pageable);
        return ResponseEntity.ok(cells);
    }

    @GetMapping("/cells/{cellId}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByCellId(@PathVariable Long cellId) {
        List<User> users = locationService.getUsersByCellId(cellId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/cells/sector/{sectorId}")
    public ResponseEntity<List<Cell>> getCellsBySectorId(@PathVariable Long sectorId) {
        List<Cell> cells = locationService.getCellsBySectorId(sectorId);
        return ResponseEntity.ok(cells);
    }

    // Village endpoints
    @GetMapping("/villages")
    public ResponseEntity<Page<Village>> getAllVillages(Pageable pageable) {
        Page<Village> villages = locationService.getAllVillages(pageable);
        return ResponseEntity.ok(villages);
    }

    @GetMapping("/villages/{villageId}/users")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByVillageId(@PathVariable Long villageId) {
        List<User> users = locationService.getUsersByVillageId(villageId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/villages/cell/{cellId}")
    public ResponseEntity<List<Village>> getVillagesByCellId(@PathVariable Long cellId) {
        List<Village> villages = locationService.getVillagesByCellId(cellId);
        return ResponseEntity.ok(villages);
    }

    // Hierarchical queries
    @GetMapping("/hierarchy/province/{provinceId}")
    public ResponseEntity<Object> getLocationHierarchyByProvinceId(@PathVariable Long provinceId) {
        Object hierarchy = locationService.getLocationHierarchyByProvinceId(provinceId);
        return ResponseEntity.ok(hierarchy);
    }

    @GetMapping("/hierarchy/district/{districtId}")
    public ResponseEntity<Object> getLocationHierarchyByDistrictId(@PathVariable Long districtId) {
        Object hierarchy = locationService.getLocationHierarchyByDistrictId(districtId);
        return ResponseEntity.ok(hierarchy);
    }

    @GetMapping("/hierarchy/sector/{sectorId}")
    public ResponseEntity<Object> getLocationHierarchyBySectorId(@PathVariable Long sectorId) {
        Object hierarchy = locationService.getLocationHierarchyBySectorId(sectorId);
        return ResponseEntity.ok(hierarchy);
    }

    @GetMapping("/hierarchy/cell/{cellId}")
    public ResponseEntity<Object> getLocationHierarchyByCellId(@PathVariable Long cellId) {
        Object hierarchy = locationService.getLocationHierarchyByCellId(cellId);
        return ResponseEntity.ok(hierarchy);
    }

    // Statistics
    @GetMapping("/stats/users-by-province")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Object> getUserStatsByProvince() {
        Object stats = locationService.getUserStatsByProvince();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/users-by-district")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Object> getUserStatsByDistrict() {
        Object stats = locationService.getUserStatsByDistrict();
        return ResponseEntity.ok(stats);
    }
}

