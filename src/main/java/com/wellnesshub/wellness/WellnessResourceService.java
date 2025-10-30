package com.wellnesshub.wellness;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WellnessResourceService {

    private final WellnessResourceRepository wellnessResourceRepository;
    private final WellnessCategoryRepository wellnessCategoryRepository;

    public WellnessResourceService(WellnessResourceRepository wellnessResourceRepository,
                                 WellnessCategoryRepository wellnessCategoryRepository) {
        this.wellnessResourceRepository = wellnessResourceRepository;
        this.wellnessCategoryRepository = wellnessCategoryRepository;
    }

    public WellnessResource createResource(WellnessResource resource) {
        if (wellnessCategoryRepository.findById(resource.getCategory().getId()).isEmpty()) {
            throw new IllegalArgumentException("Category with id " + resource.getCategory().getId() + " not found");
        }
        return wellnessResourceRepository.save(resource);
    }

    @Transactional(readOnly = true)
    public Page<WellnessResource> getAllResources(Pageable pageable) {
        return wellnessResourceRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessResource> getAllResourcesSorted(Sort sort) {
        return wellnessResourceRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Page<WellnessResource> getActiveResources(Pageable pageable) {
        return wellnessResourceRepository.findByIsActive(true, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<WellnessResource> getResourceById(Long id) {
        return wellnessResourceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<WellnessResource> getResourcesByCategory(Long categoryId, Pageable pageable) {
        WellnessCategory category = new WellnessCategory();
        category.setId(categoryId);
        return wellnessResourceRepository.findByCategory(category, pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessResource> getActiveResourcesByCategory(Long categoryId) {
        return wellnessResourceRepository.findActiveByCategoryIdOrderByTitleAsc(categoryId);
    }

    @Transactional(readOnly = true)
    public Page<WellnessResource> getResourcesByType(String resourceType, Pageable pageable) {
        return wellnessResourceRepository.findByResourceType(resourceType, pageable);
    }

    @Transactional(readOnly = true)
    public Page<WellnessResource> searchResourcesByTitle(String title, Pageable pageable) {
        return wellnessResourceRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Transactional(readOnly = true)
    public List<WellnessResource> searchResourcesByKeyword(String keyword) {
        return wellnessResourceRepository.findByKeyword(keyword);
    }

    @Transactional(readOnly = true)
    public List<WellnessResource> getResourcesByDurationRange(Integer minDuration, Integer maxDuration) {
        return wellnessResourceRepository.findByDurationRange(minDuration, maxDuration);
    }

    @Transactional(readOnly = true)
    public List<WellnessResource> getResourcesCreatedAfter(LocalDateTime fromDate) {
        return wellnessResourceRepository.findByCreatedAtAfter(fromDate);
    }

    public Optional<WellnessResource> updateResource(Long id, WellnessResource resource) {
        return wellnessResourceRepository.findById(id)
                .map(existingResource -> {
                    if (wellnessCategoryRepository.findById(resource.getCategory().getId()).isEmpty()) {
                        throw new IllegalArgumentException("Category with id " + resource.getCategory().getId() + " not found");
                    }
                    
                    existingResource.setTitle(resource.getTitle());
                    existingResource.setDescription(resource.getDescription());
                    existingResource.setResourceType(resource.getResourceType());
                    existingResource.setContentUrl(resource.getContentUrl());
                    existingResource.setDurationMinutes(resource.getDurationMinutes());
                    existingResource.setIsActive(resource.getIsActive());
                    existingResource.setCategory(resource.getCategory());
                    return wellnessResourceRepository.save(existingResource);
                });
    }

    public boolean deleteResource(Long id) {
        if (wellnessResourceRepository.existsById(id)) {
            wellnessResourceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<WellnessSession> getSessionsByResourceId(Long id) {
        Optional<WellnessResource> resource = wellnessResourceRepository.findById(id);
        return resource.map(WellnessResource::getSessions).orElse(List.of());
    }

    @Transactional(readOnly = true)
    public long getResourceCount() {
        return wellnessResourceRepository.count();
    }

    @Transactional(readOnly = true)
    public long getActiveResourceCount() {
        return wellnessResourceRepository.countByIsActive(true);
    }

    @Transactional(readOnly = true)
    public long getResourceCountByCategory(Long categoryId) {
        WellnessCategory category = new WellnessCategory();
        category.setId(categoryId);
        return wellnessResourceRepository.countByCategory(category);
    }

    @Transactional(readOnly = true)
    public long getResourceCountByType(String resourceType) {
        return wellnessResourceRepository.countByResourceType(resourceType);
    }
}

