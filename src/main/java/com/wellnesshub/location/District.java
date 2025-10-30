package com.wellnesshub.location;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "districts")
public class District {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    @Size(min = 2, max = 20)
    private String code; // Set length to 20 to match validation and avoid DB truncation errors
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;
    
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sector> sectors = new ArrayList<>();
    
    // Constructors
    public District() {}
    
    public District(String code, String name, Province province) {
        this.code = code;
        this.name = name;
        this.province = province;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }
    
    public List<Sector> getSectors() { return sectors; }
    public void setSectors(List<Sector> sectors) { this.sectors = sectors; }
    
    // Helper methods
    public void addSector(Sector sector) {
        sectors.add(sector);
        sector.setDistrict(this);
    }
    
    public void removeSector(Sector sector) {
        sectors.remove(sector);
        sector.setDistrict(null);
    }
}
