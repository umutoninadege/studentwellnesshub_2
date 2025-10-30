package com.wellnesshub.location;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provinces")
public class Province {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    @Size(min = 2, max = 20)
    private String code;
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<District> districts = new ArrayList<>();
    
    // Constructors
    public Province() {}
    
    public Province(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<District> getDistricts() { return districts; }
    public void setDistricts(List<District> districts) { this.districts = districts; }
    
    // Helper methods
    public void addDistrict(District district) {
        districts.add(district);
        district.setProvince(this);
    }
    
    public void removeDistrict(District district) {
        districts.remove(district);
        district.setProvince(null);
    }
}
