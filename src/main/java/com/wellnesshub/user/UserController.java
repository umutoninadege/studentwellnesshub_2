package com.wellnesshub.user;

import com.wellnesshub.wellness.WellnessCategory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // READ - Get all users
    @GetMapping
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    // READ - Get all users (sorted)
    @GetMapping("/sorted")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getAllUsersSorted(@RequestParam(defaultValue = "fullName") String sortBy,
                                                       @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<User> users = userService.getAllUsersSorted(sort);
        return ResponseEntity.ok(users);
    }

    // READ - Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #id)")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // READ - Get user by email
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // READ - Get users by role
    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<User>> getUsersByRole(@PathVariable Role role, Pageable pageable) {
        Page<User> users = userService.getUsersByRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    // READ - Search users by name
    @GetMapping("/search")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<User>> searchUsersByName(@RequestParam String name, Pageable pageable) {
        Page<User> users = userService.searchUsersByName(name, pageable);
        return ResponseEntity.ok(users);
    }

    // LOCATION-BASED QUERIES
    // Get users by province ID
    @GetMapping("/province/{provinceId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByProvinceId(@PathVariable Long provinceId) {
        List<User> users = userService.getUsersByProvinceId(provinceId);
        return ResponseEntity.ok(users);
    }

    // Get users by province code
    @GetMapping("/province/code/{provinceCode}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByProvinceCode(@PathVariable String provinceCode) {
        List<User> users = userService.getUsersByProvinceCode(provinceCode);
        return ResponseEntity.ok(users);
    }

    // Get users by province name
    @GetMapping("/province/name/{provinceName}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByProvinceName(@PathVariable String provinceName) {
        List<User> users = userService.getUsersByProvinceName(provinceName);
        return ResponseEntity.ok(users);
    }

    // Get users by province with pagination
    @GetMapping("/province/{provinceId}/paginated")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Page<User>> getUsersByProvinceIdPaginated(@PathVariable Long provinceId, Pageable pageable) {
        Page<User> users = userService.getUsersByProvinceIdPaginated(provinceId, pageable);
        return ResponseEntity.ok(users);
    }

    // Get users by role and province
    @GetMapping("/role/{role}/province/{provinceId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByRoleAndProvince(@PathVariable Role role, @PathVariable Long provinceId) {
        List<User> users = userService.getUsersByRoleAndProvince(role, provinceId);
        return ResponseEntity.ok(users);
    }

    // Get users by district
    @GetMapping("/district/{districtId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByDistrictId(@PathVariable Long districtId) {
        List<User> users = userService.getUsersByDistrictId(districtId);
        return ResponseEntity.ok(users);
    }

    // Get users by sector
    @GetMapping("/sector/{sectorId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersBySectorId(@PathVariable Long sectorId) {
        List<User> users = userService.getUsersBySectorId(sectorId);
        return ResponseEntity.ok(users);
    }

    // Get users by cell
    @GetMapping("/cell/{cellId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByCellId(@PathVariable Long cellId) {
        List<User> users = userService.getUsersByCellId(cellId);
        return ResponseEntity.ok(users);
    }

    // Get users by village
    @GetMapping("/village/{villageId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<List<User>> getUsersByVillageId(@PathVariable Long villageId) {
        List<User> users = userService.getUsersByVillageId(villageId);
        return ResponseEntity.ok(users);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #id)")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User user) {
        Optional<User> updatedUser = userService.updateUser(id, user);
        return updatedUser.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Vice versa: Get a user's location hierarchy
    @GetMapping("/{id}/location")
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #id)")
    public ResponseEntity<Map<String, Object>> getUserLocation(@PathVariable Long id) {
        Map<String, Object> map = userService.getUserLocationHierarchy(id);
        return ResponseEntity.ok(map);
    }

    // Many-to-Many favorites: categories
    @GetMapping("/{id}/favorites/categories")
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #id)")
    public ResponseEntity<java.util.Set<WellnessCategory>> getFavoriteCategories(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteCategories(id));
    }

    @PostMapping("/{id}/favorites/categories/{categoryId}")
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #id)")
    public ResponseEntity<Void> addFavoriteCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        userService.addFavoriteCategory(id, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/favorites/categories/{categoryId}")
    @PreAuthorize("hasRole('COUNSELOR') or @userService.isOwner(authentication.name, #id)")
    public ResponseEntity<Void> removeFavoriteCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        userService.removeFavoriteCategory(id, categoryId);
        return ResponseEntity.noContent().build();
    }

    // Additional endpoints
    @GetMapping("/count")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.getUserCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/role/{role}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Long> getUserCountByRole(@PathVariable Role role) {
        long count = userService.getUserCountByRole(role);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/province/{provinceId}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Long> getUserCountByProvinceId(@PathVariable Long provinceId) {
        long count = userService.getUserCountByProvinceId(provinceId);
        return ResponseEntity.ok(count);
    }
}
