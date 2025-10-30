package com.wellnesshub.location;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cells")
public class CellController {

    private final LocationService locationService;

    public CellController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Cell> create(@RequestBody @Valid Cell cell) {
        Cell created = locationService.createCell(cell);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cell> getById(@PathVariable Long id) {
        return locationService.getCellById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<java.util.List<Cell>> listAll() {
        return ResponseEntity.ok(locationService.getAllCellsList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cell> update(@PathVariable Long id, @RequestBody @Valid Cell cell) {
        return locationService.updateCell(id, cell).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = locationService.deleteCell(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
