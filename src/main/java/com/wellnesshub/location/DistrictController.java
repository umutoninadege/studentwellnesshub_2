package com.wellnesshub.location;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    private final LocationService locationService;

    public DistrictController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<District> create(@RequestBody @Valid District district) {
        District created = locationService.createDistrict(district);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getById(@PathVariable Long id) {
        return locationService.getDistrictById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<District> update(@PathVariable Long id, @RequestBody @Valid District district) {
        return locationService.updateDistrict(id, district).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = locationService.deleteDistrict(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<java.util.List<District>> listAll() {
        java.util.List<District> list = locationService.getAllDistrictsList();
        return ResponseEntity.ok(list);
    }
}
