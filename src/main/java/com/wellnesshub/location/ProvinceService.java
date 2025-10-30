package com.wellnesshub.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Province createProvince(Province province) {
        if (provinceRepository.existsByCode(province.getCode())) {
            throw new IllegalArgumentException("Province with code " + province.getCode() + " already exists");
        }
        if (provinceRepository.existsByName(province.getName())) {
            throw new IllegalArgumentException("Province with name " + province.getName() + " already exists");
        }
        return provinceRepository.save(province);
    }

    @Transactional(readOnly = true)
    public Page<Province> getAllProvinces(Pageable pageable) {
        return provinceRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Province> getAllProvincesSorted(Sort sort) {
        return provinceRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Optional<Province> getProvinceById(Long id) {
        return provinceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Province> getProvinceByCode(String code) {
        return provinceRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Page<Province> searchProvincesByName(String name, Pageable pageable) {
        return provinceRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional(readOnly = true)
    public List<Province> searchProvincesByKeyword(String keyword) {
        return provinceRepository.findByKeyword(keyword);
    }

    public Optional<Province> updateProvince(Long id, Province province) {
        return provinceRepository.findById(id)
                .map(existingProvince -> {
                    // Check if code is being changed and if new code already exists
                    if (!existingProvince.getCode().equals(province.getCode()) && 
                        provinceRepository.existsByCode(province.getCode())) {
                        throw new IllegalArgumentException("Province with code " + province.getCode() + " already exists");
                    }
                    // Check if name is being changed and if new name already exists
                    if (!existingProvince.getName().equals(province.getName()) && 
                        provinceRepository.existsByName(province.getName())) {
                        throw new IllegalArgumentException("Province with name " + province.getName() + " already exists");
                    }
                    
                    existingProvince.setCode(province.getCode());
                    existingProvince.setName(province.getName());
                    return provinceRepository.save(existingProvince);
                });
    }

    public boolean deleteProvince(Long id) {
        if (provinceRepository.existsById(id)) {
            provinceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<District> getDistrictsByProvinceId(Long id) {
        Optional<Province> province = provinceRepository.findById(id);
        return province.map(Province::getDistricts).orElse(List.of());
    }

    @Transactional(readOnly = true)
    public long getProvinceCount() {
        return provinceRepository.count();
    }

    @Transactional(readOnly = true)
    public long getProvinceCountByName(String name) {
        return provinceRepository.countByNameContainingIgnoreCase(name);
    }
}
