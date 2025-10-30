package com.wellnesshub.location;

import com.wellnesshub.user.User;
import com.wellnesshub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class LocationService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;
    private final UserRepository userRepository;

    public LocationService(ProvinceRepository provinceRepository,
                          DistrictRepository districtRepository,
                          SectorRepository sectorRepository,
                          CellRepository cellRepository,
                          VillageRepository villageRepository,
                          UserRepository userRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.sectorRepository = sectorRepository;
        this.cellRepository = cellRepository;
        this.villageRepository = villageRepository;
        this.userRepository = userRepository;
    }

    // Province methods
    @Transactional(readOnly = true)
    public Page<Province> getAllProvinces(Pageable pageable) {
        return provinceRepository.findAll(pageable);
    }

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

    // District methods
    @Transactional(readOnly = true)
    public Page<District> getAllDistricts(Pageable pageable) {
        return districtRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<District> getDistrictById(Long id) {
        return districtRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<District> getAllDistrictsList() {
        return districtRepository.findAll();
    }

    public District createDistrict(District district) {
        if (district.getProvince() != null && district.getProvince().getId() != null) {
            district.setProvince(provinceRepository.findById(district.getProvince().getId()).orElse(null));
        }
        return districtRepository.save(district);
    }

    public Optional<District> updateDistrict(Long id, District district) {
        return districtRepository.findById(id).map(existing -> {
            existing.setName(district.getName());
            existing.setCode(district.getCode());
            if (district.getProvince() != null && district.getProvince().getId() != null) {
                existing.setProvince(provinceRepository.findById(district.getProvince().getId()).orElse(null));
            }
            return districtRepository.save(existing);
        });
    }

    public boolean deleteDistrict(Long id) {
        if (districtRepository.existsById(id)) {
            districtRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByDistrictId(Long districtId) {
        return userRepository.findByDistrictId(districtId);
    }

    @Transactional(readOnly = true)
    public List<District> getDistrictsByProvinceId(Long provinceId) {
        return districtRepository.findByProvinceIdOrderByNameAsc(provinceId);
    }

    // Sector methods
    @Transactional(readOnly = true)
    public Page<Sector> getAllSectors(Pageable pageable) {
        return sectorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<Sector> getSectorById(Long id) {
        return sectorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<Sector> getAllSectorsList() {
        return sectorRepository.findAll();
    }

    public Sector createSector(Sector sector) {
        if (sector.getDistrict() != null && sector.getDistrict().getId() != null) {
            sector.setDistrict(districtRepository.findById(sector.getDistrict().getId()).orElse(null));
        }
        return sectorRepository.save(sector);
    }

    public Optional<Sector> updateSector(Long id, Sector sector) {
        return sectorRepository.findById(id).map(existing -> {
            existing.setName(sector.getName());
            existing.setCode(sector.getCode());
            if (sector.getDistrict() != null && sector.getDistrict().getId() != null) {
                existing.setDistrict(districtRepository.findById(sector.getDistrict().getId()).orElse(null));
            }
            return sectorRepository.save(existing);
        });
    }

    public boolean deleteSector(Long id) {
        if (sectorRepository.existsById(id)) {
            sectorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<User> getUsersBySectorId(Long sectorId) {
        return userRepository.findBySectorId(sectorId);
    }

    @Transactional(readOnly = true)
    public List<Sector> getSectorsByDistrictId(Long districtId) {
        return sectorRepository.findByDistrictIdOrderByNameAsc(districtId);
    }

    // Cell methods
    @Transactional(readOnly = true)
    public Page<Cell> getAllCells(Pageable pageable) {
        return cellRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<Cell> getCellById(Long id) {
        return cellRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<Cell> getAllCellsList() {
        return cellRepository.findAll();
    }

    public Cell createCell(Cell cell) {
        if (cell.getSector() != null && cell.getSector().getId() != null) {
            cell.setSector(sectorRepository.findById(cell.getSector().getId()).orElse(null));
        }
        return cellRepository.save(cell);
    }

    public Optional<Cell> updateCell(Long id, Cell cell) {
        return cellRepository.findById(id).map(existing -> {
            existing.setName(cell.getName());
            existing.setCode(cell.getCode());
            if (cell.getSector() != null && cell.getSector().getId() != null) {
                existing.setSector(sectorRepository.findById(cell.getSector().getId()).orElse(null));
            }
            return cellRepository.save(existing);
        });
    }

    public boolean deleteCell(Long id) {
        if (cellRepository.existsById(id)) {
            cellRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByCellId(Long cellId) {
        return userRepository.findByCellId(cellId);
    }

    @Transactional(readOnly = true)
    public List<Cell> getCellsBySectorId(Long sectorId) {
        return cellRepository.findBySectorIdOrderByNameAsc(sectorId);
    }

    // Village methods
    @Transactional(readOnly = true)
    public Page<Village> getAllVillages(Pageable pageable) {
        return villageRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<Village> getVillageById(Long id) {
        return villageRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<Village> getAllVillagesList() {
        return villageRepository.findAll();
    }

    public Village createVillage(Village village) {
        if (village.getCell() != null && village.getCell().getId() != null) {
            village.setCell(cellRepository.findById(village.getCell().getId()).orElse(null));
        }
        return villageRepository.save(village);
    }

    public Optional<Village> updateVillage(Long id, Village village) {
        return villageRepository.findById(id).map(existing -> {
            existing.setName(village.getName());
            existing.setCode(village.getCode());
            if (village.getCell() != null && village.getCell().getId() != null) {
                existing.setCell(cellRepository.findById(village.getCell().getId()).orElse(null));
            }
            return villageRepository.save(existing);
        });
    }

    public boolean deleteVillage(Long id) {
        if (villageRepository.existsById(id)) {
            villageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByVillageId(Long villageId) {
        return userRepository.findByVillageId(villageId);
    }

    @Transactional(readOnly = true)
    public List<Village> getVillagesByCellId(Long cellId) {
        return villageRepository.findByCellIdOrderByNameAsc(cellId);
    }

    // Hierarchical queries
    @Transactional(readOnly = true)
    public Map<String, Object> getLocationHierarchyByProvinceId(Long provinceId) {
        Map<String, Object> hierarchy = new HashMap<>();
        
        Province province = provinceRepository.findById(provinceId).orElse(null);
        if (province != null) {
            hierarchy.put("province", province);
            hierarchy.put("districts", province.getDistricts());
            hierarchy.put("userCount", userRepository.countByProvince(province));
        }
        
        return hierarchy;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getLocationHierarchyByDistrictId(Long districtId) {
        Map<String, Object> hierarchy = new HashMap<>();
        
        District district = districtRepository.findById(districtId).orElse(null);
        if (district != null) {
            hierarchy.put("district", district);
            hierarchy.put("province", district.getProvince());
            hierarchy.put("sectors", district.getSectors());
            hierarchy.put("userCount", userRepository.countByDistrict(district));
        }
        
        return hierarchy;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getLocationHierarchyBySectorId(Long sectorId) {
        Map<String, Object> hierarchy = new HashMap<>();
        
        Sector sector = sectorRepository.findById(sectorId).orElse(null);
        if (sector != null) {
            hierarchy.put("sector", sector);
            hierarchy.put("district", sector.getDistrict());
            hierarchy.put("province", sector.getDistrict().getProvince());
            hierarchy.put("cells", sector.getCells());
            hierarchy.put("userCount", userRepository.countBySector(sector));
        }
        
        return hierarchy;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getLocationHierarchyByCellId(Long cellId) {
        Map<String, Object> hierarchy = new HashMap<>();
        
        Cell cell = cellRepository.findById(cellId).orElse(null);
        if (cell != null) {
            hierarchy.put("cell", cell);
            hierarchy.put("sector", cell.getSector());
            hierarchy.put("district", cell.getSector().getDistrict());
            hierarchy.put("province", cell.getSector().getDistrict().getProvince());
            hierarchy.put("villages", cell.getVillages());
            hierarchy.put("userCount", userRepository.countByCell(cell));
        }
        
        return hierarchy;
    }

    // Statistics
    @Transactional(readOnly = true)
    public Map<String, Object> getUserStatsByProvince() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Province> provinces = provinceRepository.findAll();
        Map<String, Long> provinceStats = new HashMap<>();
        
        for (Province province : provinces) {
            long userCount = userRepository.countByProvince(province);
            provinceStats.put(province.getName(), userCount);
        }
        
        stats.put("usersByProvince", provinceStats);
        stats.put("totalUsers", userRepository.count());
        
        return stats;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getUserStatsByDistrict() {
        Map<String, Object> stats = new HashMap<>();
        
        List<District> districts = districtRepository.findAll();
        Map<String, Long> districtStats = new HashMap<>();
        
        for (District district : districts) {
            long userCount = userRepository.countByDistrict(district);
            districtStats.put(district.getName() + " (" + district.getProvince().getName() + ")", userCount);
        }
        
        stats.put("usersByDistrict", districtStats);
        stats.put("totalUsers", userRepository.count());
        
        return stats;
    }
}

