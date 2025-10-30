package com.wellnesshub.location;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    private final LocationService locationService;

    public SectorController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Sector> create(@RequestBody @Valid Sector sector) {
        Sector created = locationService.createSector(sector);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sector> getById(@PathVariable Long id) {
        return locationService.getSectorById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<java.util.List<Sector>> listAll() {
        return ResponseEntity.ok(locationService.getAllSectorsList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sector> update(@PathVariable Long id, @RequestBody @Valid Sector sector) {
        return locationService.updateSector(id, sector).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = locationService.deleteSector(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
