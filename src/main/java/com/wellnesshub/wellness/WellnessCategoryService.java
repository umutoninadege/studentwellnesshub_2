package com.wellnesshub.wellness;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WellnessCategoryService {

    private final WellnessCategoryRepository wellnessCategoryRepository;

    public WellnessCategoryService(WellnessCategoryRepository wellnessCategoryRepository) {
        this.wellnessCategoryRepository = wellnessCategoryRepository;
    }

    public WellnessCategory createCategory(WellnessCategory category) {
        if (wellnessCategoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        return wellnessCategoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Page<WellnessCategory> getAllCategories(Pageable pageable) {
        return wellnessCategoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessCategory> getAllCategoriesSorted(Sort sort) {
        return wellnessCategoryRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public List<WellnessCategory> getActiveCategories() {
        return wellnessCategoryRepository.findActiveCategoriesOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public Optional<WellnessCategory> getCategoryById(Long id) {
        return wellnessCategoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<WellnessCategory> getCategoryByName(String name) {
        return wellnessCategoryRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Page<WellnessCategory> searchCategoriesByName(String name, Pageable pageable) {
        return wellnessCategoryRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessCategory> searchCategoriesByKeyword(String keyword) {
        return wellnessCategoryRepository.findByKeyword(keyword);
    }

    public Optional<WellnessCategory> updateCategory(Long id, WellnessCategory category) {
        return wellnessCategoryRepository.findById(id)
                .map(existingCategory -> {
                    // Check if name is being changed and if new name already exists
                    if (!existingCategory.getName().equals(category.getName()) && 
                        wellnessCategoryRepository.existsByName(category.getName())) {
                        throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
                    }
                    
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    existingCategory.setIsActive(category.getIsActive());
                    return wellnessCategoryRepository.save(existingCategory);
                });
    }

    public boolean deleteCategory(Long id) {
        if (wellnessCategoryRepository.existsById(id)) {
            wellnessCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<WellnessResource> getResourcesByCategoryId(Long id) {
        Optional<WellnessCategory> category = wellnessCategoryRepository.findById(id);
        return category.map(WellnessCategory::getResources).orElse(List.of());
    }

    @Transactional(readOnly = true)
    public long getCategoryCount() {
        return wellnessCategoryRepository.count();
    }

    @Transactional(readOnly = true)
    public long getActiveCategoryCount() {
        return wellnessCategoryRepository.countByIsActive(true);
    }

    @Transactional(readOnly = true)
    public long getCategoryCountByName(String name) {
        return wellnessCategoryRepository.countByNameContainingIgnoreCase(name);
    }
}

