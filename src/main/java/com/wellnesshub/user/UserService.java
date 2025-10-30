package com.wellnesshub.user;

import com.wellnesshub.location.Province;
import com.wellnesshub.location.District;
import com.wellnesshub.location.Sector;
import com.wellnesshub.location.Cell;
import com.wellnesshub.location.Village;
import com.wellnesshub.wellness.WellnessCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final com.wellnesshub.wellness.WellnessCategoryRepository wellnessCategoryRepository;

    public UserService(UserRepository userRepository, com.wellnesshub.wellness.WellnessCategoryRepository wellnessCategoryRepository) {
        this.userRepository = userRepository;
        this.wellnessCategoryRepository = wellnessCategoryRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsersSorted(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Page<User> getUsersByRole(Role role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    @Transactional(readOnly = true)
    public Page<User> searchUsersByName(String name, Pageable pageable) {
        return userRepository.findByFullNameContainingIgnoreCase(name, pageable);
    }

    // Location-based queries
    @Transactional(readOnly = true)
    public List<User> getUsersByProvinceId(Long provinceId) {
        return userRepository.findByProvinceId(provinceId);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByProvinceCode(String provinceCode) {
        return userRepository.findByProvinceCode(provinceCode);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByProvinceName(String provinceName) {
        return userRepository.findByProvinceName(provinceName);
    }

    @Transactional(readOnly = true)
    public Page<User> getUsersByProvinceIdPaginated(Long provinceId, Pageable pageable) {
        return userRepository.findByProvinceId(provinceId, pageable);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRoleAndProvince(Role role, Long provinceId) {
        Province province = new Province();
        province.setId(provinceId);
        return userRepository.findByRoleAndProvince(role, province);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByDistrictId(Long districtId) {
        District district = new District();
        district.setId(districtId);
        return userRepository.findByDistrict(district);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersBySectorId(Long sectorId) {
        Sector sector = new Sector();
        sector.setId(sectorId);
        return userRepository.findBySector(sector);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByCellId(Long cellId) {
        Cell cell = new Cell();
        cell.setId(cellId);
        return userRepository.findByCell(cell);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByVillageId(Long villageId) {
        Village village = new Village();
        village.setId(villageId);
        return userRepository.findByVillage(village);
    }

    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Check if email is being changed and if new email already exists
                    if (!existingUser.getEmail().equals(user.getEmail()) && 
                        userRepository.existsByEmail(user.getEmail())) {
                        throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
                    }
                    
                    existingUser.setEmail(user.getEmail());
                    existingUser.setFullName(user.getFullName());
                    existingUser.setRole(user.getRole());
                    existingUser.setActive(user.isActive());
                    existingUser.setVillage(user.getVillage());
                    return userRepository.save(existingUser);
                });
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public long getUserCount() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    public long getUserCountByRole(Role role) {
        return userRepository.countByRole(role);
    }

    @Transactional(readOnly = true)
    public long getUserCountByProvinceId(Long provinceId) {
        Province province = new Province();
        province.setId(provinceId);
        return userRepository.countByProvince(province);
    }

    // Security helper method
    public boolean isOwner(String email, Long userId) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(u -> u.getId().equals(userId)).orElse(false);
    }

    // Favorites (Many-to-Many)
    public void addFavoriteCategory(Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow();
        WellnessCategory category = wellnessCategoryRepository.findById(categoryId).orElseThrow();
        user.addFavoriteCategory(category);
        userRepository.save(user);
    }

    public void removeFavoriteCategory(Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow();
        WellnessCategory category = wellnessCategoryRepository.findById(categoryId).orElseThrow();
        user.removeFavoriteCategory(category);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public java.util.Set<WellnessCategory> getFavoriteCategories(Long userId) {
        return userRepository.findById(userId).orElseThrow().getFavoriteCategories();
    }

    // User location hierarchy (vice versa lookup)
    @Transactional(readOnly = true)
    public Map<String, Object> getUserLocationHierarchy(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Map<String, Object> map = new HashMap<>();
        Village village = user.getVillage();
        if (village != null) {
            Cell cell = village.getCell();
            Sector sector = cell != null ? cell.getSector() : null;
            District district = sector != null ? sector.getDistrict() : null;
            Province province = district != null ? district.getProvince() : null;
            map.put("province", province);
            map.put("district", district);
            map.put("sector", sector);
            map.put("cell", cell);
            map.put("village", village);
        }
        return map;
    }
}
