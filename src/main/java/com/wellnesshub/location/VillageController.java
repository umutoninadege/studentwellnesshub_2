package com.wellnesshub.location;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/villages")
public class VillageController {

    private final LocationService locationService;

    public VillageController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Village> create(@RequestBody @Valid Village village) {
        Village created = locationService.createVillage(village);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Village> getById(@PathVariable Long id) {
        return locationService.getVillageById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<java.util.List<Village>> listAll() {
        return ResponseEntity.ok(locationService.getAllVillagesList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Village> update(@PathVariable Long id, @RequestBody @Valid Village village) {
        return locationService.updateVillage(id, village).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = locationService.deleteVillage(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
