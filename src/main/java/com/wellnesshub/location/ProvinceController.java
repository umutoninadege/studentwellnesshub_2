package com.wellnesshub.location;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Province> createProvince(@RequestBody @Valid Province province) {
        Province createdProvince = provinceService.createProvince(province);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProvince);
    }

    // READ - Get all provinces
    @GetMapping
    public ResponseEntity<Page<Province>> getAllProvinces(Pageable pageable) {
        Page<Province> provinces = provinceService.getAllProvinces(pageable);
        return ResponseEntity.ok(provinces);
    }

    // READ - Get all provinces (sorted)
    @GetMapping("/sorted")
    public ResponseEntity<List<Province>> getAllProvincesSorted(@RequestParam(defaultValue = "name") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<Province> provinces = provinceService.getAllProvincesSorted(sort);
        return ResponseEntity.ok(provinces);
    }

    // READ - Get province by ID
    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        Optional<Province> province = provinceService.getProvinceById(id);
        return province.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // READ - Get province by code
    @GetMapping("/code/{code}")
    public ResponseEntity<Province> getProvinceByCode(@PathVariable String code) {
        Optional<Province> province = provinceService.getProvinceByCode(code);
        return province.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // READ - Search provinces by name
    @GetMapping("/search")
    public ResponseEntity<Page<Province>> searchProvincesByName(@RequestParam String name, Pageable pageable) {
        Page<Province> provinces = provinceService.searchProvincesByName(name, pageable);
        return ResponseEntity.ok(provinces);
    }

    // READ - Search provinces by keyword
    @GetMapping("/search/keyword")
    public ResponseEntity<List<Province>> searchProvincesByKeyword(@RequestParam String keyword) {
        List<Province> provinces = provinceService.searchProvincesByKeyword(keyword);
        return ResponseEntity.ok(provinces);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable Long id, @RequestBody @Valid Province province) {
        Optional<Province> updatedProvince = provinceService.updateProvince(id, province);
        return updatedProvince.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        boolean deleted = provinceService.deleteProvince(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Additional endpoints
    @GetMapping("/{id}/districts")
    public ResponseEntity<List<District>> getDistrictsByProvinceId(@PathVariable Long id) {
        List<District> districts = provinceService.getDistrictsByProvinceId(id);
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getProvinceCount() {
        long count = provinceService.getProvinceCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/search")
    public ResponseEntity<Long> getProvinceCountByName(@RequestParam String name) {
        long count = provinceService.getProvinceCountByName(name);
        return ResponseEntity.ok(count);
    }
}
